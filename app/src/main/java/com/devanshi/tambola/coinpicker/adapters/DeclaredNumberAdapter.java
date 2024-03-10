package com.devanshi.tambola.coinpicker.adapters;

import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.recyclerview.widget.*;

import com.devanshi.tambola.coinpicker.*;

import java.util.*;

public class DeclaredNumberAdapter extends RecyclerView.Adapter<DeclaredNumberAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Integer> declaredNumberArrayList;

    public DeclaredNumberAdapter(Context c, ArrayList<Integer> declaredNumberArrayList) {
        mContext = c;
        this.declaredNumberArrayList = declaredNumberArrayList;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View listItem = layoutInflater.inflate(R.layout.item_declared_number, parent, false);
        return new ViewHolder(listItem);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Integer number = declaredNumberArrayList.get(position);
        holder.grid.setText(String.format("%s",number));
        holder.view.setBackground(getDrawables());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return declaredNumberArrayList.size();
    }

    public void refreshList(ArrayList<Integer> menuArrayList) {

        this.declaredNumberArrayList = menuArrayList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView grid;
        View view;

        ViewHolder(View itemView) {
            super(itemView);
            this.grid = itemView.findViewById(R.id.grid_text);
            this.view = itemView.findViewById(R.id.bgview);
        }
    }

    private int previousColor = 0;

    private GradientDrawable getDrawables(){
        List<String> colors;

        colors= new ArrayList<>();

        colors.add("#5E97F6");
        colors.add("#9CCC65");
        colors.add("#FF8A65");
        colors.add("#9E9E9E");
        colors.add("#9FA8DA");
        colors.add("#90A4AE");
        colors.add("#AED581");
        colors.add("#F6BF26");
        colors.add("#FFA726");
        colors.add("#4DD0E1");
        colors.add("#BA68C8");
        colors.add("#A1887F");

        Random r = new Random();
        int i1 = r.nextInt(11);

        while(i1 == previousColor){
            i1 = r.nextInt(11);
        }
        previousColor = i1;

        //generating shape with colors

        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.parseColor(colors.get(i1)));
        return draw;
    }
}
