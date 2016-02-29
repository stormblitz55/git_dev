import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ibm.icu.util.StringTokenizer;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.wb.swt.SWTResourceManager;


public class CreateQRCode {

	protected Shell shell;
	
	private String filepath = ""; 
	private static File selectedFile = null;
	private static File selectedir = null;
	//private static int pcnt = 19;
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CreateQRCode window = new CreateQRCode();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(737, 500);
		shell.setText("SWT Application");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
				JFrame window = new JFrame();
				JFileChooser fileChooser = new JFileChooser();
		         
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "//" + "Desktop"));
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt 파일", "txt");
				
				fileChooser.addChoosableFileFilter(filter);
		        //파일오픈 다이얼로그 를 띄움
		        int result = fileChooser.showOpenDialog(window);
		         
		        if (result == JFileChooser.APPROVE_OPTION) {
		            //선택한 파일의 경로 반환
		            selectedFile = fileChooser.getSelectedFile();
		            //filepath =  fileChooser.
		            //경로 출력
		            JOptionPane.showMessageDialog(null, "QRCode 생성 파일이 선택되었습니다.\n"+selectedFile.toString());
		           
		        }
			}
		});
		btnNewButton.setBounds(88, 173, 243, 35);
		btnNewButton.setText("QR Code \uC0DD\uC131 \uD30C\uC77C\uC120\uD0DD");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("맑은 고딕", 11, SWT.NORMAL));
		lblNewLabel.setBounds(88, 27, 539, 27);
		lblNewLabel.setText("\uD30C\uC77C\uACFC \uBC15\uC2A4 \uC218\uB7C9, \uC0DD\uC131\uC704\uCE58\uB97C \uC120\uD0DD \uD6C4 QRCode \uC0DD\uC131 \uBC84\uD2BC\uC744 \uD074\uB9AD\uD558\uC138\uC694");
		//text.setData(key, value);
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.setFont(SWTResourceManager.getFont("맑은 고딕", 12, SWT.BOLD));
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				try {
					System.out.println("befor");
					System.out.println(selectedFile);
					createQRCode();
				} catch (WriterException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(360, 173, 134, 128);
		btnNewButton_1.setText("QRCode\uC0DD\uC131");
		
		Button btnNewButton_2 = new Button(shell, SWT.NONE);
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				JFrame window = new JFrame();
				JFileChooser fileChooser = new JFileChooser();
		         
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "//" + "Desktop"));
				//JFileChooser.DIRECTORIES_ONLY;
				//FileNameExtensionFilter filter = new FileNameExtensionFilter("txt 파일", "txt");
				
				//fileChooser.addChoosableFileFilter(filter);
		        //파일오픈 다이얼로그 를 띄움
		        int result = fileChooser.showDialog(window, "선택");
		         
		        if (result == JFileChooser.APPROVE_OPTION) {
		            //선택한 파일의 경로 반환
		            selectedir = fileChooser.getSelectedFile();
		            //filepath =  fileChooser.
		            //경로 출력
		            JOptionPane.showMessageDialog(null, "QRCode가 생성될 폴더가 선택되었습니다.\n"+selectedir.toString());
		           
		        }
				
			}
		});
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton_2.setBounds(88, 266, 243, 35);
		btnNewButton_2.setText("QR Code \uC0DD\uC131 \uD3F4\uB354 \uC120\uD0DD");
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(85, 113, 542, -6);
		
		Label lblAaaa = new Label(shell, SWT.BORDER | SWT.SEPARATOR | SWT.WRAP | SWT.HORIZONTAL | SWT.SHADOW_IN | SWT.CENTER);
		lblAaaa.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblAaaa.setText("aaaa");
		lblAaaa.setBounds(88, 90, 533, 4);
		
		Label label_1 = new Label(shell, SWT.WRAP | SWT.SHADOW_IN);
		label_1.setFont(SWTResourceManager.getFont("맑은 고딕", 10, SWT.BOLD));
		label_1.setText("\uBC15\uC2A4 \uC81C\uD488\uC218\uB7C9 \uC120\uD0DD");
		label_1.setBounds(88, 68, 134, 16);
		
		Label label_2 = new Label(shell, SWT.BORDER | SWT.SEPARATOR | SWT.WRAP | SWT.HORIZONTAL | SWT.SHADOW_IN | SWT.CENTER);
		label_2.setText("aaaa");
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_2.setBounds(88, 163, 243, 4);
		
		Label lblData = new Label(shell, SWT.WRAP | SWT.SHADOW_IN);
		lblData.setText("\uC0DD\uC131 DATA \uD30C\uC77C\uC120\uD0DD");
		lblData.setFont(SWTResourceManager.getFont("맑은 고딕", 10, SWT.BOLD));
		lblData.setBounds(88, 140, 134, 16);
		
		Label lblQrCode = new Label(shell, SWT.WRAP | SWT.SHADOW_IN);
		lblQrCode.setText("QR Code \uC0DD\uC131\uD3F4\uB354 \uC120\uD0DD");
		lblQrCode.setFont(SWTResourceManager.getFont("맑은 고딕", 10, SWT.BOLD));
		lblQrCode.setBounds(88, 231, 161, 16);
		
		Label label_4 = new Label(shell, SWT.BORDER | SWT.SEPARATOR | SWT.WRAP | SWT.HORIZONTAL | SWT.SHADOW_IN | SWT.CENTER);
		label_4.setText("aaaa");
		label_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_4.setBounds(88, 253, 243, 4);

	}
	
	private static void createQRCode() throws WriterException, IOException {
		System.out.println("createQRCode");
		
		String qrCodeText = "";
		String filename = "";
		
		
		
		/*
		ReadData.append("Total Cnt : 10EA");
		ReadData.append('\n');
		ReadData.append("Product Bar Code :");
		ReadData.append('\n');
		ReadData.append("12345678900001");
		ReadData.append('\n');
		ReadData.append("12345678900002");
		ReadData.append('\n');
		ReadData.append("12345678900003");
		ReadData.append('\n');
		ReadData.append("12345678900004");
		ReadData.append('\n');
		ReadData.append("12345678900005");
		ReadData.append('\n');
		ReadData.append("12345678900006");
		ReadData.append('\n');
		ReadData.append("12345678900007");
		ReadData.append('\n');
		ReadData.append("12345678900008");
		ReadData.append('\n');
		ReadData.append("12345678900009");
		ReadData.append('\n');
		ReadData.append("12345678900010");
		ReadData.append('\n');
		*/
		
		String QRHeader = "";
		String QRBody   = "";
		
		int codecnt = 0;
		int filecnt = 1;

	
		BufferedReader readFile = new BufferedReader(new FileReader(selectedFile));
		String readdata = new String();
		System.out.println(selectedFile);
		System.out.println("aaaaa");
		
		//읽어서 넣기
		StringBuffer wrData = new StringBuffer();
		String QRContent = new String();
		//String Modelnm = "DR65";
		//String Modelnm = null;
		
		
		try{
			while(readdata != null){
				
				System.out.println("bbbb");
				readdata = readFile.readLine().trim();
				System.out.println("cccc");
				//StringTokenizer st = new StringTokenizer(readdata, ",");
				String[] st = stringSplit(readdata, ",");
				System.out.println("dddd");
				//wrData.append('\n');
				System.out.println("eeee");
				//wrData.append(readdata);
				//QRContent = 
				filename  = st[1].substring(2);
				//codecnt ++;
				
				/**
				if(codecnt > pcnt){
					QRHeader = " - Total Cnt : "+Integer.toString(codecnt);
					QRBody = wrData.toString();
					//Modelnm = QRBody.substring(1, 5);
					qrCodeText = "Model Name : "+Modelnm+QRHeader+QRBody;
					System.out.println(qrCodeText);
					String filePath = selectedir.toString()+"\\"+Modelnm+"_"+Integer.toString(codecnt)+"EA_"+filecnt+".png";
					int size = 170;
					String fileType = "png";
					File qrFile = new File(filePath);
					createQRImage(qrFile, qrCodeText, size, fileType);
					
					wrData = null;
					wrData = new StringBuffer();
					codecnt  = 0;
					filecnt ++;
				}
				*/
				
				System.out.println(filename);
				//QRHeader = "";
				QRBody = readdata;
				//Modelnm = QRBody.substring(1, 5);
				qrCodeText = QRBody;
				System.out.println(qrCodeText);
				String filePath = selectedir.toString()+"\\"+filename+".png";
				int size = 170;
				String fileType = "png";
				File qrFile = new File(filePath);
				createQRImage(qrFile, qrCodeText, size, fileType);
					
				wrData = null;
				//wrData = new StringBuffer();
				codecnt  = 0;
				filecnt ++;
				
				
			}
			
			
		}catch(Exception e){
			if(codecnt < 1) {
				System.out.println(e);
			}else{
				//QRHeader = " - Total Cnt : "+Integer.toString(codecnt);
				QRBody = wrData.toString();
				//Modelnm = QRBody.substring(1, 5);
				qrCodeText = QRBody;
				System.out.println(qrCodeText);
				String endfilePath = selectedir.toString()+"\\"+filename+".png";
				int size = 170;
				String endfileType = "png";
				File qrFile = new File(endfilePath);
				createQRImage(qrFile, qrCodeText, size, endfileType);
			}
			
		}
		JOptionPane.showMessageDialog(null, "QRCode 생성이 완료되었습니다.");
		
	}
	
	public static String[] stringSplit(String string, String onToken ) {
		final StringTokenizer tokenizer = new StringTokenizer( string, onToken );
		final String[] result = new String[ tokenizer.countTokens() ];
		
		for( int i = 0; i < result.length; i++ ){
			result[i] = tokenizer.nextToken();
		}
		return result;
		}
	
	private static void createQRImage(File qrFile, String qrCodeText, int size, String fileType) throws WriterException, IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
		Hashtable hintMap = new Hashtable();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText,
		BarcodeFormat.QR_CODE, size, size, hintMap);
		// Make the BufferedImage that are to hold the QRCode
		
		// 원래의 이미지 사이즈
		int matrixWidth = byteMatrix.getWidth();		
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth,	BufferedImage.TYPE_INT_RGB);

		//int matrixWidth1 = 595;
		//int matrixHeigth = 842;
		//BufferedImage image = new BufferedImage(matrixWidth, matrixWidth,	BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
			 
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setBackground(Color.WHITE);
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		// Paint and save the image using the ByteMatrix
		graphics.setColor(Color.BLACK);
		
		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		ImageIO.write(image, fileType, qrFile);
		System.out.println("create QRCode");
	}
	
	
	
	private static void createQRImage2(File qrFile, String qrCodeText, int size, String fileType) throws WriterException, IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
		Hashtable hintMap = new Hashtable();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText,
		BarcodeFormat.QR_CODE, size, size, hintMap);
		// Make the BufferedImage that are to hold the QRCode
		
		
		int fmatrixWidth = 595;
		int fmatrixHeigth = 842;
		
		
		
		BufferedImage image = new BufferedImage(fmatrixWidth, fmatrixHeigth,	BufferedImage.TYPE_INT_RGB);
		// 원래의 이미지 사이즈
		int matrixWidth = byteMatrix.getWidth();		
		//BufferedImage image = new BufferedImage(matrixWidth, matrixWidth,	BufferedImage.TYPE_INT_RGB);
		
		
		//BufferedImage image = new BufferedImage(fmatrixWidth, fmatrixHeigth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
			 
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		//graphics.fillRect(0, 0, fmatrixWidth, fmatrixHeigth);
		// Paint and save the image using the ByteMatrix
		graphics.setColor(Color.BLACK);
		
		
		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		ImageIO.write(image, fileType, qrFile);
	}
}
