package pkgCore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import pkgConstants.*;
import pkgEnum.eCardNo;
import pkgEnum.eHandStrength;
import pkgEnum.eRank;
import pkgEnum.eSuit;

public class HandPoker extends Hand {

	private ArrayList<CardRankCount> CRC = null;

	public HandPoker() {
		this.setHS(new HandScorePoker());
	}

	protected ArrayList<CardRankCount> getCRC() {
		return CRC;
	}

	@Override
	public HandScore ScoreHand() {
		// TODO : Implement this method... call each of the 'is' methods (isRoyalFlush,
		// etc) until
		// one of the hands is true, then score the hand

		Collections.sort(super.getCards());
		Frequency();

		if(isRoyalFlush()) 
		{

		} 
		else if(isStraightFlush()) 
		{

		}
		else if(isFourOfAKind())
		{
			
		}
		else if(isFullHouse())
		{
			
		}
		else if(isFlush())
		{
			
		}
		else if(isStraight())
		{
			
		}
		else if(isThreeOfAKind())
		{
			
		}
		else if(isTwoPair())
		{
			
		}
		else if(isPair())
		{
			
		}
		else if(isHighCard())
		{
			
		}
		return null;
	}

	public boolean isRoyalFlush() {

		// TODO : Implement this method
		return (this.isStraightFlush() && this.getCards().get(0).geteRank() == eRank.ACE);
	}

	public boolean isStraightFlush() 
	{

		// TODO : Implement this method
		return (this.isFlush() && this.isStraight());
	}
	
	// TODO : Implement this method
	public boolean isFourOfAKind() 
	{
		
		return (this.getCards().get(0).geteRank() == this.getCards().get(3).geteRank() 
				|| this.getCards().get(1).geteRank() == this.getCards().get(4).geteRank());
		
	}

	// TODO : Implement this method
	public boolean isFullHouse() 
	{
		int size = this.getCards().size();
		HashSet<eRank> uniqueRanks = new HashSet<>();
		
		for(int i = 0; i < size; i++)
		{
			uniqueRanks.add(this.getCards().get(i).geteRank());
		}
		
		return (uniqueRanks.size() == 2 && (this.getCards().get(0).geteRank() == this.getCards().get(2).geteRank() 
				|| this.getCards().get(2).geteRank() == this.getCards().get(4).geteRank()));
	}

	public boolean isFlush() 
	{
		boolean bisFlush = false;

		int iCardCnt = super.getCards().size();
		int iSuitCnt = 0;

		for (eSuit eSuit : eSuit.values()) {
			for (Card c : super.getCards()) {
				if (eSuit == c.geteSuit()) {
					iSuitCnt++;
				}
			}
			if (iSuitCnt > 0)
				break;
		}

		if (iSuitCnt == iCardCnt)
			bisFlush = true;
		else
			bisFlush = false;

		return bisFlush;
	}

	public boolean isStraight() 
	{
		boolean bisStraight = false;
	
		// TODO : Implement this method
		if(this.getCards().get(0).geteRank() == eRank.ACE && this.getCards().get(1).geteRank() != eRank.KING)
		{
			if(this.getCards().get(1).geteRank() == eRank.FIVE && this.getCards().get(4).geteRank() == eRank.TWO)
			{
				bisStraight = true;
			}
		}
		else
		{
			if(this.getCards().get(0).geteRank().getiRankNbr() == this.getCards().get(4).geteRank().getiRankNbr() + 4)
			{
				bisStraight = true;
			}
		}
		
		return bisStraight;
	}

	// This is how to implement one of the 'counting' hand types. Testing to see if
	// there are three of the same rank.
	public boolean isThreeOfAKind() 
	{
		boolean bisThreeOfAKind = false;
		if (this.getCRC().size() == 3) {
			if (this.getCRC().get(0).getiCnt() == Constants.THREE_OF_A_KIND) {
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.ThreeOfAKind);
				int iGetCard = this.getCRC().get(0).getiCardPosition();
				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(null);
				HSP.setKickers(FindTheKickers(this.getCRC()));
				this.setHS(HSP);
				bisThreeOfAKind = true;
			}
		}
		return bisThreeOfAKind;
	}

	public boolean isTwoPair() {
		// TODO : Implement this method
		int size = this.getCards().size();
		HashSet<eRank> uniqueRanks = new HashSet<>();
		
		for(int i = 0; i < size; i++)
		{
			uniqueRanks.add(this.getCards().get(i).geteRank());
		}
		return uniqueRanks.size() == 3;
	}

	public boolean isPair() {
		// TODO : Implement this method
		int size = this.getCards().size();
		HashSet<eRank> uniqueRanks = new HashSet<>();
		
		for(int i = 0; i < size; i++)
		{
			uniqueRanks.add(this.getCards().get(i).geteRank());
		}
		
		return (uniqueRanks.size() == 4);
	}

	public boolean isHighCard() {
		boolean bisHighCard = true;
		// TODO : Implement this method
		return bisHighCard;
	}

	private ArrayList<Card> FindTheKickers(ArrayList<CardRankCount> CRC) {
		ArrayList<Card> kickers = new ArrayList<Card>();

		for (CardRankCount crcCheck : CRC) {
			if (crcCheck.getiCnt() == 1) {
				kickers.add(this.getCards().get(crcCheck.getiCardPosition()));
			}
		}

		return kickers;
	}

	private void Frequency() {
		CRC = new ArrayList<CardRankCount>();
		int iCnt = 0;
		int iPos = 0;
		for (eRank eRank : eRank.values()) {
			iCnt = (CountRank(eRank));
			if (iCnt > 0) {
				iPos = FindCardRank(eRank);
				CRC.add(new CardRankCount(eRank, iCnt, iPos));
			}
		}
		Collections.sort(CRC);
	}

	private int CountRank(eRank eRank) {
		int iCnt = 0;
		for (Card c : super.getCards()) {
			if (c.geteRank() == eRank) {
				iCnt++;
			}
		}
		return iCnt;
	}

	private int FindCardRank(eRank eRank) {
		int iPos = 0;

		for (iPos = 0; iPos < super.getCards().size(); iPos++) {
			if (super.getCards().get(iPos).geteRank() == eRank) {
				break;
			}
		}
		return iPos;
	}

}
