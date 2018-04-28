package engine;

import java.util.ArrayList;
import java.util.Random;

public class Society {
	
	private ArrayList<Player> society = new ArrayList<Player>();
	
	public ArrayList<Player> new_society(int players){
		
		Random random = new Random();
		boolean male;
		int money;
		
		for (int i=0;i < players - 1;i++){
			male = random.nextBoolean();
			money = random.nextInt(4500) + 500;
			if (male) { 
				society.add(new Player(NameGenerator.generateMaleName(), male, money, null ,false));
			}	else	{
				society.add(new Player(NameGenerator.generateFemaleName(), male, money, null ,false));
			}
		}
		
		return society;
	}
}
