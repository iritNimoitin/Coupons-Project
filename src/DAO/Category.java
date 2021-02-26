package DAO;

public enum Category {
	Food, Electricity, Restaurant, Vacation, Sport, Furnitures, Clothing, Attractions;
	
	public static int categoryNum(Category category) {
		switch(category) {
		case Food:
			return 1;
		case Electricity:
			return 2;
		case  Restaurant:
			return 3;
		case Vacation:
			return 4;
		case Sport:
			return 5;
		case Furnitures:
			return 6;
		case Clothing:
			return 7;
		case Attractions:
			return 8;
		default :
			return -1;
		}
	}
}
