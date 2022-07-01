package aop;

public class UserServiceImpl implements UserService{

	@Override
	public void add() {
		// TODO Auto-generated method stub
		System.out.println("增加用戶");
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		System.out.println("刪除用戶");
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		System.out.println("更新用戶");
	}

	@Override
	public void select() {
		// TODO Auto-generated method stub
		System.out.println("查詢用戶");
		
	}

}
