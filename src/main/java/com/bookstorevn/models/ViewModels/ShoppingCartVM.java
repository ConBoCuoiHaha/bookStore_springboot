package com.bookstorevn.models.ViewModels;

import com.bookstorevn.models.OrderHeader;
import com.bookstorevn.models.ShoppingCart;
import java.util.List;

public class ShoppingCartVM {
    private List<ShoppingCart> shoppingCartList;
    private OrderHeader orderHeader;

    public ShoppingCartVM() {}

    public List<ShoppingCart> getShoppingCartList() { return shoppingCartList; }
    public void setShoppingCartList(List<ShoppingCart> shoppingCartList) { this.shoppingCartList = shoppingCartList; }

    public OrderHeader getOrderHeader() { return orderHeader; }
    public void setOrderHeader(OrderHeader orderHeader) { this.orderHeader = orderHeader; }
}
