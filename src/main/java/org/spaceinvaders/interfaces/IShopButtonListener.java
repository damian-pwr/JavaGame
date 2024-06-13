package org.spaceinvaders.interfaces;

import org.spaceinvaders.entities.ShopButton;

/**
 * Shop button listener
 */
public interface IShopButtonListener {
    /**
     * This function is ran when player shots specified button and has enough currency to buy it
     * @param btn Button object that ran this
     * @return True to allow purchase
     */
    public boolean onPurchase(ShopButton btn);
}
