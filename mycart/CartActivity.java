package edu.hanu.mycart;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rcvPInCart;
    private CartAdapter cartAdapter;
    private List<Product>productListInCart;
    public TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tvTotal=findViewById(R.id.tvTotal);
        rcvPInCart=findViewById(R.id.rcvCart);

        ProductManager productManager=ProductManager.getInstance(this);
        productListInCart=productManager.allInCart();

        cartAdapter = new CartAdapter(productListInCart,this);
        rcvPInCart.setAdapter(cartAdapter);
        rcvPInCart.setLayoutManager(new LinearLayoutManager(this));

        long total=0;
        for(Product product:productListInCart){
            total+=Long.parseLong(product.getUnitPrice().substring(1))*product.getQuantity();

        }
        tvTotal.setText(total+"đ");
    }
}