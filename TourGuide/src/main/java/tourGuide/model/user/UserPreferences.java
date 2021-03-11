package tourGuide.model.user;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;


public class UserPreferences {
	
	private int attractionProximity = Integer.MAX_VALUE;

	private CurrencyUnit currency = Monetary.getCurrency("USD");

	private Money lowerPricePoint = Money.of(0, currency);

	private Money highPricePoint = Money.of(Integer.MAX_VALUE, currency);

	private int tripDuration = 1;

	private int ticketQuantity = 1;

	private int numberOfAdults = 1;

	private int numberOfChildren = 0;
	
	public UserPreferences() {
	}

	public UserPreferences(final int attractionProximity, final Money lowerPricePoint, final Money highPricePoint,
						   final int tripDuration, final int ticketQuantity, final int numberOfAdults,
						   final int numberOfChildren) {
		this.attractionProximity = attractionProximity;
		this.lowerPricePoint = lowerPricePoint;
		this.highPricePoint = highPricePoint;
		this.tripDuration = tripDuration;
		this.ticketQuantity = ticketQuantity;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
	}

	public void setAttractionProximity(final int attractionProximity) {
		this.attractionProximity = attractionProximity;
	}
	
	public int getAttractionProximity() {
		return attractionProximity;
	}
	
	public Money getLowerPricePoint() {
		return lowerPricePoint;
	}

	public void setLowerPricePoint(final Money lowerPricePoint) {
		this.lowerPricePoint = lowerPricePoint;
	}

	public Money getHighPricePoint() {
		return highPricePoint;
	}

	public void setHighPricePoint(final Money highPricePoint) {
		this.highPricePoint = highPricePoint;
	}
	
	public int getTripDuration() {
		return tripDuration;
	}

	public void setTripDuration(final int tripDuration) {
		this.tripDuration = tripDuration;
	}

	public int getTicketQuantity() {
		return ticketQuantity;
	}

	public void setTicketQuantity(final int ticketQuantity) {
		this.ticketQuantity = ticketQuantity;
	}
	
	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(final int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(final int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
}
