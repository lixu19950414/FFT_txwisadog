package application;

import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

final class RecorderButton extends Button {
	private boolean record = true; // Record or play-back
	String btnName = "Record";
	Capture capture = null;
	PlayBack playBack = null;
	boolean doneRecord = false;
	
	AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
	float rate = 8000.0f;
	int sampleSize = 16;
	boolean bigEndian = true;
	int channels = 1;
	boolean signed = true;
	
	public RecorderButton() {
		super("Record");
		this.setOnAction((ActionEvent e) -> {
			if (this.record) {
				this.doneRecord = false;
				this.capture = new Capture();
				this.capture.start();
				this.record = false;
				this.btnName = "Stop";
				//设置所有的其他按钮为不可用
				Scene_Choose.btn.setDisable(true);
				Scene_Choose.btnNext.setDisable(true);
				Scene_Choose.btnPrev.setDisable(true);
				Scene_Choose.playBack.setDisable(true);
				Scene_Choose.btnReset.setDisable(true);
				Scene_Choose.tf.setDisable(true);
			}
			else {
				Main.inRealArray = this.capture.stopAndReturnData();
				this.capture = null;
				this.record = true;
				this.doneRecord = true;
				this.btnName = "Record";
				Scene_Choose.btnNext.setDisable(false);
				if (!Scene_Choose.controls.getChildren().contains(Scene_Choose.playBack))
					Scene_Choose.controls.getChildren().addAll(Scene_Choose.playBack);
				Scene_Choose.playBack.setVisible(true);
				Scene_Choose.playBack.setDisable(false);
				Scene_Choose.btnReset.setDisable(false);
			}
			this.setText(this.btnName);
		});
	}
	
	public void setPlayBack() {
		this.setOnAction((ActionEvent e) -> {
			this.setText("Play Back");
			this.playBackUsingArray(Main.inRealArray);
		});
	}
	
	public void playBackUsingArray(float []dataSource) {
		this.playBack = new PlayBack();
		float []ir = dataSource.clone();
		float []ii = new float[ir.length];
		float []or = new float[ii.length];
		float []oi = new float[or.length];
		for (int i = 0; i < ir.length; i++) {
			ii[i] = 0; 
		}
		InteractWithC.FFT(ir.length, false, ir, ii, or, oi);
		InteractWithC.FFT(ir.length, true, or, oi, ir, ii);
		playBack.start(ir);
//		playBack.start(dataSource);
	}
	
	class Capture implements Runnable {
		AudioInputStream audioInputStream;
		TargetDataLine line;
		Thread thread;
		float []capturedAudioSignal;
		boolean captured = false;
		
		float[] stopAndReturnData() {
			this.stop();
			while (!this.captured) {
				try {
					Thread.sleep(100);
//					System.out.println("Thread.sleep");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			float []data = this.capturedAudioSignal.clone();
			return data;
		}
		
		void start() {
			this.thread = new Thread(this);
			this.thread.setName("Recoder");
			this.thread.start();
		}

		void stop() {
			this.thread = null;
		}
		
		@Override
		public void run() {
			AudioFormat format = new AudioFormat(rate, sampleSize, channels, signed, bigEndian);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			if (!AudioSystem.isLineSupported(info)) {
				System.err.println("Line matching " + info + "not supported.");
				return;
			}
			try {
				this.line = (TargetDataLine) AudioSystem.getLine(info);
				this.line.open(format, this.line.getBufferSize());
			} catch (LineUnavailableException e) {
				e.printStackTrace();
				System.err.println("Unable to open the line: " + e);
				return;
			} catch (SecurityException e) {
				System.err.println(e);
				return;
			} catch (Exception e) {
				System.err.println(e);
				return;
			}
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			int frameSizeInBytes = format.getFrameSize();
			int bufferLengthInFrames = this.line.getBufferSize() / 8;
			int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
			byte []audioData = new byte[bufferLengthInBytes];
			int numByteRead = 0;
			
			line.start();
			while (null != this.thread) {
				if ( -1 != (numByteRead = line.read(audioData, 0, bufferLengthInBytes)) ) {
					outstream.write(audioData, 0, numByteRead);
				}
			}
			line.close();
			this.capturedAudioSignal = this.extractFloatDataFromAmplitudeByteArray(format, outstream.toByteArray());
			this.captured = true;
			System.out.println("Size of signal captured: " +	 this.capturedAudioSignal.length);
			return;
		}
		
		private float[] extractFloatDataFromAmplitudeByteArray(AudioFormat format, byte[] audioBytes) {
			// convert
			float []audioData = null;
			if (format.getSampleSizeInBits() == 16) {
				int nlengthInSamples = audioBytes.length / 2;
				audioData = new float[nlengthInSamples];
				if (format.isBigEndian()) {
					for (int i = 0; i < nlengthInSamples; i++) {
						/* First byte is MSB (high order) */
						int MSB = audioBytes[2 * i];
						/* Second byte is LSB (low order) */
						int LSB = audioBytes[2 * i + 1];
						audioData[i] = MSB << 8 | (255 & LSB);
					}
				}
				else {
					for (int i = 0; i < nlengthInSamples; i++) {
						/* First byte is LSB (low order) */
						int LSB = audioBytes[2 * i];
						/* Second byte is MSB (high order) */
						int MSB = audioBytes[2 * i + 1];
						audioData[i] = MSB << 8 | (255 & LSB);
					}
				}
			}
			else if (format.getSampleSizeInBits() == 8) {
				int nlengthInSamples = audioBytes.length;
				audioData = new float[nlengthInSamples];
				if (format.getEncoding().toString().startsWith("PCM_SIGN")) {
					for (int i = 0; i < audioBytes.length; i++) {
						audioData[i] = audioBytes[i];
					}
				}
				else {
					for (int i = 0; i < audioBytes.length; i++) {
						audioData[i] = audioBytes[i] - 128;
					}
				}
			}// end of if..else
			// System.out.println("PCM Returned===============" +
				// audioData.length);
			return audioData;
		}
		
	}

	class PlayBack implements Runnable {
		byte []audioData = null;
		SourceDataLine line  = null;
		final static int buffSize = 500;
		Thread thread = null;
		
		private byte[] convertToByteArray(float []source) {
			float max = 0;
			for (int i = 0; i < source.length; i++) {
				max = (max < Math.abs(source[i]) ? Math.abs(source[i]) : max);
			}
			float []buff = source.clone();
			for (int i = 0; i < buff.length; i++) {
				buff[i] /= max; 
			}
			byte []target = null;
			if (8 == sampleSize) {
				target = new byte[buff.length];
				int offset = (signed ? 0 : 128);
				int tmp = 0;
				for (int i = 0; i < buff.length; i++) {
					tmp = (int) (buff[i]*128. + 0.5);
					tmp = Math.min(tmp, 127);
					tmp = Math.max(tmp, -128);
					target[i] = (byte) (tmp + offset);
				}
			}
			else if (16 == sampleSize) {
				target = new byte[2 * buff.length];
				int offset = (signed ? 0 : 1 << 15);
				short tmp = 0;
				if (bigEndian) {
					for (int i = 0; i < buff.length; i++) {
						tmp = (short) (buff[i] * 32768. + 0.5);
						tmp += offset;
						target[2*i] = (byte) ((tmp & 0xff00) >> 8);
						target[2*i+1] = (byte) (tmp & 0x00ff);
					}
				}
				else {
					for (int i = 0; i < buff.length; i++) {
						tmp = (short) (buff[i] * 32768. + 0.5);
						tmp += offset;
						target[2*i+1] = (byte) ((tmp & 0xff00) >> 8);
						target[2*i] = (byte) (tmp & 0x00ff);
					}
				}
			} else { System.err.println("Error: sampleSize = " + sampleSize); }
			return target;
		}

		public void start(float []audioFloatArray) {
			this.audioData = this.convertToByteArray(audioFloatArray);
			this.thread = new Thread(this);
			this.thread.start();
		}
		
		public void stop() {
			this.thread = null;
		}
		
		@Override
		public void run() {
			AudioFormat format = new AudioFormat(rate, sampleSize, channels, signed, bigEndian);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			try {
				this.line = (SourceDataLine) AudioSystem.getLine(info);
				this.line.open(format);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
			int writeNum = this.audioData.length / PlayBack.buffSize;
			this.line.start();
			for (int i = 0; i < writeNum; i++) {
				line.write(this.audioData, PlayBack.buffSize * i, PlayBack.buffSize);
			}
			int remnantSize = this.audioData.length - PlayBack.buffSize * writeNum;
			line.write(this.audioData, PlayBack.buffSize * writeNum, remnantSize);
			line.close();
			return;
		}
	}

}
