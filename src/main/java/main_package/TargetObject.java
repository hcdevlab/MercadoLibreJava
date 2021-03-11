package main_package;

public class TargetObject {
	private String objectId;
	private String categoryId;
	private String categoryName;
	private String productName;

	public TargetObject(String objectId, String categoryId, String categoryName, String productName) {
		this.objectId = objectId;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.productName = productName;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
