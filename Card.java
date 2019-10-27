package JavaPoker;

public class Card
{
	private static String[] Suit = {"Hearts", "Clubs", "Spades", "Diamonds"};
	private static String[] Rank = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
	private short suit, rank;

	//constructor
	public static Card(short suit, short rank)
	{
		this.rank = rank;
		this.suit = suit;
	}

	public static String RankAsString(int _rank)
	{  return Rank[_rank];  }
	public short getRank()  {  return rank;  }
	public short getSuit()  {  return suit;  }
}

inport java.util.Random
inport java.util.ArrayList

public class Deck
{
	private ArrayList<Card> Deck;

	Deck()
	{
		cards = new ArrayList<Card>();
		int index1, index2; 
		Random generator = new Random();
		Card tempCard;

		for(int i=1;i<=4;i++)
		{
			for(j=1;j<=13;j++)
			{
				cards.Add(new Card(i,j));
			}
		}

		int size;
		//shuffles the deck
		for(int i=0;i<100;i++)
		{
			index1 = generator.nextInt(cards.size()-1);
			index2 = generator.nextInt(cards.size()-1);
			tempCard = cards.get(index2);
			cards.set(index2, cards.get(index1));
			cards.set(index1, tempCard);
		}
	}

	public Card PullFromDeck()
	{  cards.remove(0);  }
	public int returnTotalCards()
	{  return cards.size();  }
}