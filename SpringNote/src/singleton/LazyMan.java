package singleton;

public class LazyMan {

	private byte[] data1 = new byte[1024 * 1024];
	private byte[] data2 = new byte[1024 * 1024];
	private byte[] data3 = new byte[1024 * 1024];
	private byte[] data4 = new byte[1024 * 1024];

    //1.建構子私有化
	private LazyMan() {
	}

	//2.創建LazyMan的變量，但沒有具體引用，這就是差異，為了避免資源的浪費，只要這個類沒有被使用，則不創建，待調用方法時才創建，
	private static LazyMan LazyMan;

	public static LazyMan getInstance() {
		if(LazyMan==null) {
			LazyMan=new LazyMan();
		}
		return LazyMan;
	}

}
