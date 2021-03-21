package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Intolerance;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddIntolerancesOrAdditivesXAdapter extends
    RecyclerView.Adapter<AddIntolerancesOrAdditivesXAdapter.Holderview> {

  private final Map<String, Object> itemsListUntouchable;
  private Map<String, Object> itemsList = new HashMap<>();
  private Context context;
  private FirebaseUser user;
  private FirebaseFirestore db;
  private AddIntolerancesOrAdditives addIntolerancesOrAdditives;

  public AddIntolerancesOrAdditivesXAdapter(Map<String, Object> itemsList, Context context,
      FirebaseUser user,
      FirebaseFirestore db) {
    this.itemsList = itemsList;
    this.context = context;
    this.itemsListUntouchable = itemsList;
    this.user = user;
    this.db = db;
    try {
      if (!(((MainActivity) context).getClass() == MainActivity.class)) {
        addIntolerancesOrAdditives = (AddIntolerancesOrAdditives) context;
      }
    } catch (Exception e) {
      Log.d(this.getClass().getName(), "Controlled exception.");
      addIntolerancesOrAdditives = (AddIntolerancesOrAdditives) context;
    }

  }


  @Override
  public Holderview onCreateViewHolder(ViewGroup parent, int viewType) {
    View layout = LayoutInflater.from(parent.getContext()).
        inflate(R.layout.custom_intolerance_item_x, parent, false);
    return new Holderview(layout);
  }

  @Override
  public void onBindViewHolder(Holderview holder, final int position) {

    holder.v_name.setText((String) itemsList.keySet().toArray()[position]);
    holder.v_image.setImageResource(R.drawable.ic_intolerances);
    holder.v_delete.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (itemsList.get(itemsList.keySet().toArray()[position]).getClass() == Intolerance.class) {
          db.collection("USERS").document(user.getUid())
              .update("intolerances", FieldValue.arrayRemove(itemsList.keySet().toArray()[position]));
        } else {
          db.collection("USERS").document(user.getUid())
              .update("unsupported_additives", FieldValue.arrayRemove(itemsList.keySet().toArray()[position]));
        }
      }
    });
  }


  @Override
  public int getItemCount() {
    return itemsList.size();
  }

  public void setfilter(List<String> listitem) {
    itemsList = new HashMap<>();
    for (String str : listitem) {
      itemsList.put(str, itemsListUntouchable.get(str));
    }
    notifyDataSetChanged();
  }

  class Holderview extends RecyclerView.ViewHolder {

    ImageView v_image;
    TextView v_name;
    ImageButton v_delete;

    Holderview(View itemview) {
      super(itemview);
      v_image = (ImageView) itemview.findViewById(R.id.product_image);
      v_name = (TextView) itemView.findViewById(R.id.product_title);
      v_delete = itemview.findViewById(R.id.delete_item);
    }
  }
}