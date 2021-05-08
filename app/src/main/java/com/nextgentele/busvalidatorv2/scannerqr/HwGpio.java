package com.nextgentele.busvalidatorv2.scannerqr;

public class HwGpio {

		/**
		 * USB模式 - host模式
		 */
		public static final int USB_MODE_HOST = 0;

		/**
		 * USB模式 - device模式
		 */
		public static final int USB_MODE_DEVICE	= 1;

		/**
		 * Uart模式 - QRCODE模式
		 */
		public static final int UART_MODE_QRCODE = 0;

		/**
		 * UART模式 - RS232模式
		 */
		public static final int UART_MODE_RS232 = 1;

		static
		{
			try {
				System.loadLibrary("hw_gpio");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	/**
	 * Power 灯 点亮
	 * @return
	 */
	public int HW_PowerLED_On() {
		return Native_HW_PowerLED_On();
	}

	/**
	 * Power 灯 熄灭
	 * @return
	 */
	public int HW_PowerLED_Off() {
		return Native_HW_PowerLED_Off();
	}

	/**
	 * Com 灯 点亮
	 * @return
	 */
	public int HW_ComLED_On() {
		return Native_HW_ComLED_On();
	}

	/**
	 * Com 灯 熄灭
	 * @return
	 */
	public int HW_ComLED_Off() {
		return Native_HW_ComLED_Off();
	}

	/**
	 * Gps 灯 点亮
	 * @return
	 */
	public int HW_GpsLED_On() {
		return Native_HW_GpsLED_On();
	}

	/**
	 * Gps 灯 熄灭
	 * @return
	 */
	public int HW_GpsLED_Off() {
		return Native_HW_GpsLED_Off();
	}

	/**
	 * Card 灯 点亮
	 * @return
	 */
	public int HW_CardLED_On() {
		return Native_HW_CardLED_On();
	}

	/**
	 * Card 灯 熄灭
	 * @return
	 */
	public int HW_CardLED_Off() {
		return Native_HW_CardLED_Off();
	}

		/**
		 * usb模式配置 控制
		 * @param mode	0-host模式	1-device模式
		 * @return
		 */
		public int HW_UsbMode_Set(int mode) {
			return Native_HW_UsbMode_Set(mode);
		}

	/**
	 * 数码管显示初始化
	 * @return
	 */
	public int HW_SLCD_Init() {
		return Native_HW_SLCD_Init();
	}

	/**
	 * 数码管显示数据
	 * @param data 4字节  ASCII 示例：0x31,0x32,0x33,0x34 -> 显示1234
	 * @return
	 */
	public int HW_SLCD_DisStr(byte[] data) {
		return Native_HW_SLCD_DisStr(data);
	}

	/**
	 * 数码管显示数据
	 * @param data 字符 示例：“1234” ->显示1234
	 * @return
	 */
	public int HW_SLCD_DisStr(String data) {
		return Native_HW_SLCD_DisStrS(data);
	}

	/**
	 * 数码管显示关
	 * @return
	 */
	public int HW_SLCD_DisOff() {
		return Native_HW_SLCD_DisOff();
	}

	/**
	 * 外接5V电源
	 * @param 1 打开 0 关闭
	 * @return
	 */
	public int HW_TTY_pwren(int flag) {
		return Native_HW_TTYpwren_flag(flag);
	}

	private native int Native_HW_PowerLED_On();
	private native int Native_HW_PowerLED_Off();
	private native int Native_HW_ComLED_On();
	private native int Native_HW_ComLED_Off();
	private native int Native_HW_GpsLED_On();
	private native int Native_HW_GpsLED_Off();
	private native int Native_HW_CardLED_On();
	private native int Native_HW_CardLED_Off();
	private native int Native_HW_UsbMode_Set(int mode);
	private native int Native_HW_SLCD_Init();
	private native int Native_HW_SLCD_DisStr(byte[] data);
	private native int Native_HW_SLCD_DisStrS(String data);
	private native int Native_HW_SLCD_DisOff();
	private native int Native_HW_TTYpwren_flag(int flag);
}
