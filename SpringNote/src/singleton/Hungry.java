package singleton;

public class Hungry {

	private byte[] data1 = new byte[1024 * 1024];
	private byte[] data2 = new byte[1024 * 1024];
	private byte[] data3 = new byte[1024 * 1024];
	private byte[] data4 = new byte[1024 * 1024];

	// 1.建構子私有化，外部無法創建該對象
	private Hungry() {
	}

	// 2.自己創建對象，變數被final修飾後，不能再指向其他對象，但指向對象的內容是可以被改變的
	private final static Hungry HUNGRY = new Hungry();

    // 3.提供對應的獲取方法
	public static Hungry getInstance() {
		return HUNGRY;
	}
}
