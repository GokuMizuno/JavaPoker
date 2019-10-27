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

public class Hand 
{
	private Card[] cards;
	private int[] value;

	Hand(Deck d)
	{
		value = new int[6];
		cards = new Card[5];
		for (int x=0; x<5; x++)
		{
			cards[x] = d.drawFromDeck();
		}

		//get score for hand
		/*Even if there is no matching cards, there will be a high card, and a low card.
		  The high card goes into int numSameCardsHigh
		  The second highest goes into numSameCardsLow
		  If you have 4 of a kind, it will go into one slot, and the 5th card goes into the second
		*/
		int[] ranks = new int[14];
		int[] orderedRanks = new int[5];	 //miscellaneous cards that are not otherwise significant
		boolean flush=true, straight=false;
		int sameCards=1,sameCards2=1;
		int largeGroupRank=0,smallGroupRank=0;
		int index=0;
		int topStraightValue=0;

		for (int x=0; x<=13; x++)
		{
			ranks[x]=0;
		}
		for (int x=0; x<=4; x++)
		{
			ranks[ cards[x].getRank() ]++;
		}
		for (int x=0; x<4; x++) {
			if ( cards[x].getSuit() != cards[x+1].getSuit() )
				flush=false;
		}


		for (int x=13; x>=1; x--)
		{
				 if (ranks[x] > sameCards)
				 {
					 if (sameCards != 1)  //if sameCards was not the default value
					 {
						 sameCards2 = sameCards;
						 smallGroupRank = largeGroupRank;
					 }

					 sameCards = ranks[x];
					 largeGroupRank = x;

				 } else if (ranks[x] > sameCards2)
				 {
					 sameCards2 = ranks[x];
					 smallGroupRank = x;
				 }
		 }


		if (ranks[1]==1) //if ace, run this before because ace is highest card
		{
			orderedRanks[index]=14;
			index++;
		}

		for (int x=13; x>=2; x--)
		{
			if (ranks[x]==1)
			{
				orderedRanks[index]=x; //if ace
				index++;
			}
		}
		
		

		
		for (int x=1; x<=9; x++) //can't have straight with lowest value of more than 10
		{
			if (ranks[x]==1 && ranks[x+1]==1 && ranks[x+2]==1 && ranks[x+3]==1 && ranks[x+4]==1)
			{
				straight=true;
				topStraightValue=x+4; //4 above bottom value
				break;
			}
		}

		if (ranks[10]==1 && ranks[11]==1 && ranks[12]==1 && ranks[13]==1 && ranks[1]==1) //ace high
		{
			straight=true;
			topStraightValue=14; //higher than king
		}
		
		for (int x=0; x<=5; x++)
		{
			value[x]=0;
		}


		 //start hand evaluation
		if ( sameCards==1 ) {
			value[0]=1;
			value[1]=orderedRanks[0];
			value[2]=orderedRanks[1];
			value[3]=orderedRanks[2];
			value[4]=orderedRanks[3];
			value[5]=orderedRanks[4];
		}

		if (sameCards==2 && sameCards2==1)
		{
			value[0]=2;
			value[1]=largeGroupRank; //rank of pair
			value[2]=orderedRanks[0];
			value[3]=orderedRanks[1];
			value[4]=orderedRanks[2];
		}

		if (sameCards==2 && sameCards2==2) //two pair
		{
			value[0]=3;
			value[1]= largeGroupRank>smallGroupRank ? largeGroupRank : smallGroupRank; //rank of greater pair
			value[2]= largeGroupRank<smallGroupRank ? largeGroupRank : smallGroupRank;
			value[3]=orderedRanks[0];  //extra card
		}

		if (sameCards==3 && sameCards2!=2)
		{
			value[0]=4;
			value[1]= largeGroupRank;
			value[2]=orderedRanks[0];
			value[3]=orderedRanks[1];
		}

		if (straight && !flush)
		{
			value[0]=5;
			value[1]=topStraightValue;
		}

		if (flush && !straight)
		{
			value[0]=6;
			value[1]=orderedRanks[0]; //tie determined by ranks of cards
			value[2]=orderedRanks[1];
			value[3]=orderedRanks[2];
			value[4]=orderedRanks[3];
			value[5]=orderedRanks[4];
		}

		if (sameCards==3 && sameCards2==2)
		{
			value[0]=7;
			value[1]=largeGroupRank;
			value[2]=smallGroupRank;
		}

		if (sameCards==4)
		{
			value[0]=8;
			value[1]=largeGroupRank;
			value[2]=orderedRanks[0];
		}

		if (straight && flush)
		{
			value[0]=9;
			value[1]=topStraightValue;
		}
	}

	void display()
	{
		String s;
		switch( value[0] )
		{

			case 1:
				s="high card";
				break;
			case 2:
				s="pair of " + Card.rankAsString(value[1]) + "\'s";
				break;
			case 3:
				s="two pair " + Card.rankAsString(value[1]) + " " + Card.rankAsString(value[2]);
				break;
			case 4:
				s="three of a kind " + Card.rankAsString(value[1]) + "\'s";
				break;
			case 5:
				s=Card.rankAsString(value[1]) + " high straight";
				break;
			case 6:
				s="flush";
				break;
			case 7:
				s="full house " + Card.rankAsString(value[1]) + " over " + Card.rankAsString(value[2]);
				break;
			case 8:
				s="four of a kind " + Card.rankAsString(value[1]);
				break;
			case 9:
				s="straight flush " + Card.rankAsString(value[1]) + " high";
				break;
			default:
				s="error in Hand.display: value[0] contains invalid value";
		}
		s = "				" + s;
		System.out.println(s);
	}

	void displayAll()
	{
		for (int x=0; x<5; x++)
			System.out.println(cards[x]);
	}

	int compareTo(Hand that)
	{
		for (int x=0; x<6; x++)
		{
			if (this.value[x]>that.value[x])  {  return 1;  }
			else if (this.value[x]<that.value[x])  {  return -1;  }
		}
		return 0; //if hands are equal
	}
}

