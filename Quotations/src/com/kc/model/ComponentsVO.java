package com.kc.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;



public class ComponentsVO {

	private int id;
	
	private String componentName;
	
	private String componentCategory;
	
	private String subCategory;
	
	private String vendor;
	
	private String model;
	
	private String type;
	
	private String size;
	
	private DoubleProperty costPrice;
	
	private DoubleProperty dealerPrice;
	
	private DoubleProperty endUserPrice;
	
    private final IntegerProperty quantity ;
    
    private final ReadOnlyDoubleWrapper totalCostPrice ;
    private final ReadOnlyDoubleWrapper totalDealerPrice ;
    private final ReadOnlyDoubleWrapper totalEndUserPrice ;
    

    public ComponentsVO()
    {
    	this.quantity = new SimpleIntegerProperty(this, "quantity", 1);
    	this.costPrice = new SimpleDoubleProperty(this, "costPrice", 1);
    	this.dealerPrice = new SimpleDoubleProperty(this, "dealerPrice", 1);
    	this.endUserPrice = new SimpleDoubleProperty(this, "endUserPrice", 1);
    	this.totalCostPrice = new ReadOnlyDoubleWrapper(this, "totalCostPrice");
    	this.totalDealerPrice = new ReadOnlyDoubleWrapper(this, "totalDealerPrice");
    	this.totalEndUserPrice = new ReadOnlyDoubleWrapper(this, "totalEndUserPrice");
        
        // bind total price to product of price and quantity:
    	totalCostPrice.bind(costPrice.multiply(quantity));
    	totalDealerPrice.bind(dealerPrice.multiply(quantity));
    	totalEndUserPrice.bind(endUserPrice.multiply(quantity));
    }
    
    
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getComponentCategory() {
		return componentCategory;
	}

	public void setComponentCategory(String componentCategory) {
		this.componentCategory = componentCategory;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}



	public Double getCostPrice() {
		return costPrice.get();
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice.set(costPrice);
	}

	 public final DoubleProperty costPriceProperty() {
	        return costPrice ;
	    }

	 public Double getDealerPrice() {
			return dealerPrice.get();
		}



		public void setDealerPrice(Double dealerPrice) {
			this.dealerPrice.set(dealerPrice);
		}

		 public final DoubleProperty dealerPriceProperty() {
		        return dealerPrice ;
		    }

	 public Double getEndUserPrice() {
			return endUserPrice.get();
		}



		public void setEndUserPrice(Double endUserPrice) {
			this.endUserPrice.set(endUserPrice);
		}

	 public final DoubleProperty endUserPriceProperty() {
	        return endUserPrice ;
	    }


	public final void setQuantity(int quantity) {
	       this.quantity.set(quantity);
	}
	
	public Integer getQuantity() {
		return quantity.get();
	}
	
	public final IntegerProperty quantityProperty() {
        return quantity ;
    }
	public final double getTotalCostPrice() {
        return totalCostPrice.get();
    }
    public final ReadOnlyDoubleProperty totalCostPriceProperty() {
        return totalCostPrice.getReadOnlyProperty();
    }
    public final double getTotalDealerPrice() {
        return totalDealerPrice.get();
    }
    public final ReadOnlyDoubleProperty totalDealerPriceProperty() {
        return totalDealerPrice.getReadOnlyProperty();
    }
    public final double getTotalEndUserPrice() {
        return totalEndUserPrice.get();
    }
    public final ReadOnlyDoubleProperty totalEndUserPriceProperty() {
        return totalEndUserPrice.getReadOnlyProperty();
    }
    
	public String toString()
	{
		return this.componentName;
	}
}
