package edu.hanu.mycart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.BitSet;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private List<Product> products;
    private CartActivity cartAct;

    public CartAdapter(List<Product> products, CartActivity cartAct) {
        this.products = products;
        this.cartAct = cartAct;

    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_in_cart, parent, false);

        return new CartHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder cartHolder, int position) {
        Product product = products.get(position);

        cartHolder.bind(product);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        private ImageView imvPInCart;
        private TextView tvNamePInCart, tvPriceInCart, tvSumPrice, tvQuantity;
        private ImageButton plusBtn, minusBtn;
        private Context context;

        public CartHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            imvPInCart = itemView.findViewById(R.id.imvPInCart);
            tvNamePInCart = itemView.findViewById(R.id.tvNamePInCart);
            tvPriceInCart = itemView.findViewById(R.id.tvPriceInCart);
            tvSumPrice = itemView.findViewById(R.id.tvSumPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            plusBtn = itemView.findViewById(R.id.plusBtn);
            minusBtn = itemView.findViewById(R.id.minusBtn);
        }

        public void bind(Product product) {
            tvNamePInCart.setText(product.getName());
            tvPriceInCart.setText(String.valueOf(product.getUnitPrice()));
            tvQuantity.setText(String.valueOf(product.getQuantity()));
            tvSumPrice.setText(String.valueOf(Integer.parseInt(product.getUnitPrice()) * product.getQuantity()));

            ImageLoader imageLoader = new ImageLoader();
            imageLoader.execute(product.getThumbnail());

            //plus quantity
            plusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductManager productManager = ProductManager.getInstance(context);
                    int quantityUpdated = (int) (product.getQuantity() + 1);
                    product.setQuantity(quantityUpdated);
                    productManager.update(product);
                    Log.i("Product", product.getQuantity() + "");
                    tvSumPrice.setText(String.valueOf(Integer.parseInt(product.getUnitPrice()) * product.getQuantity()));

                    long totalPrice = 0;
                    for (Product product1 : productManager.all()) {
                        totalPrice += Long.parseLong(product1.getUnitPrice().substring(1)) * product1.getQuantity();

                    }
                    cartAct.tvTotal.setText(totalPrice + "đ");

                    notifyDataSetChanged();

                }
            });
            //minus quantity
            minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductManager productManager = ProductManager.getInstance(context);

                    if (product.getQuantity() == 1) {
                        new AlertDialog.Builder(context)
                                .setMessage("Do you want to remove this product?")
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        products.remove(product);
                                        int quantityAfterUdapte = (int) (product.getQuantity() - 1);
                                        product.setQuantity((int) quantityAfterUdapte);
                                        productManager.update(product);
                                        long totalPrice = 0;
                                        for (Product p : productManager.all()) {
                                            totalPrice += Long.parseLong(p.getUnitPrice().substring(1)) * p.getQuantity();

                                        }
                                        cartAct.tvTotal.setText(totalPrice + "đ");
                                        Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                    }
                                })
                                .show();

                    } else {
                        int quantityAfterUpdate = (int) (product.getQuantity() - 1);
                        product.setQuantity(quantityAfterUpdate);
                        productManager.update(product);
                        tvSumPrice.setText(String.valueOf(Integer.parseInt(product.getUnitPrice().substring(1)) * product.getQuantity()));
                        long totalPrice = 0;
                        for (Product p : productManager.all()) {
                            totalPrice += Long.parseLong(p.getUnitPrice().substring(1)) * p.getQuantity();

                        }
                        cartAct.tvTotal.setText(totalPrice + "đ");
                        notifyDataSetChanged();
                    }

                }
            });
        }

        public class ImageLoader extends AsyncTask<String, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(String... strings) {
                URL url;
                HttpURLConnection httpURLConnection;
                try {
                    url = new URL(strings[0]);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
            @Override
            protected void onPostExecute(Bitmap bitmap){
                super.onPostExecute(bitmap);
                imvPInCart.setImageBitmap(bitmap);
            }
        }
    }
}
