package JavaPoker;

public class User(int? coins)
{
	int bet = 0;
	int _coins = coins;
	bool isBusted = false;  //goes true when you are out
	enum status = {"fold", "call", "raise", "quit"};
	Hand Userhand = new Hand();

	public enum ChangeStatus()
	{
	}


}