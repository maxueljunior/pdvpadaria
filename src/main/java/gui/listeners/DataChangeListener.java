package gui.listeners;

import pdv.model.entities.Item;

public interface DataChangeListener {
	
	void onDataChanged();
	void onSelect(Item item);
	
}
