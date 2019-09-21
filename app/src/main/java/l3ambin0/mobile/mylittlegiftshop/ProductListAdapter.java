package l3ambin0.mobile.mylittlegiftshop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductHolder> {

    protected List<ProductCard> items;

    public static class ProductHolder extends RecyclerView.ViewHolder {
        // Campos de la lista
        public TextView title;
        public TextView descr;
        public TextView price;
        public ImageView img;
        public int product_id;
        public Button btn_add_to_cart;

        public ProductHolder(View v) {
            super(v);
            title = v.findViewById(R.id.product_title);
            descr = v.findViewById(R.id.product_description);
            price = v.findViewById(R.id.product_price);
            img = v.findViewById(R.id.product_image);
            btn_add_to_cart = v.findViewById(R.id.btn_add_to_cart);
        }
    }

    public ProductListAdapter(List<ProductCard> items) {
        this.items = items;
    }

    /*
    Añade una lista completa de items
     */
    public void addAll(List<ProductCard> lista){
        items.addAll(lista);
        notifyDataSetChanged();
    }

    /*
    Permite limpiar todos los elementos del recycler
     */
    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_card, viewGroup, false);
        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductHolder viewHolder, int i) {
        viewHolder.title.setText(items.get(i).getTitle());
        viewHolder.descr.setText(items.get(i).getDescription());
        viewHolder.price.setText(String.valueOf(items.get(i).getPrice()));
        String img_path = items.get(i).getImage();
        float rotation = Utils.getCameraPhotoOrientation(img_path);
        Utils.putOnImageView(viewHolder.img, img_path, rotation);
//        viewHolder.product_id = items.get(i).getId();

        final int prd_id;
        prd_id = i;
        Button.OnClickListener btn_click = new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Utils.addToCart(items.get(prd_id));
            }
        };

        viewHolder.btn_add_to_cart.setOnClickListener(btn_click);

    }
}