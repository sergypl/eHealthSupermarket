package com.gmail.gigi.dan2011.ehealthsupermarket.ui.list;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.gmail.gigi.dan2011.ehealthsupermarket.AccountSettings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;

/**
 * Javadoc comment.
 */
public class MyListFragment extends Fragment {


  private TextInputLayout editTxt;
  private ListView list;
  private Button btn;
  private ArrayList<RowItem> arrayList = new ArrayList<>();
  private CustomAdapter adapter;
  private Dialog myDialog;
  private MyListViewModel mylistViewModel;
  private ListView individual_row;


  /* Stuff in this function will be executed when the fragment View is creating.
   *  in this case we are inflating the elements from fragment_mylist layout file to be able to
   *  use them in this class, that's why it's returning the View. */
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mylistViewModel = new ViewModelProvider(this).get(MyListViewModel.class);
    View root = inflater.inflate(R.layout.fragment_mylist, container, false);

    individual_row = root.findViewById(R.id.main_list);
    adapter = new CustomAdapter(getContext(), arrayList);
    individual_row.setAdapter(adapter);
    individual_row.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent bigFlagIntent = new Intent(getContext(), MyListProducts.class);
        startActivity(bigFlagIntent);
      }
    });
    return root;
  }
  ////////////////////////////////////////////


  /* A function which we call to add elements to the main list of items. */
  private void fillArrayList(String heading, String subHeading, int smallImg, int bigImg) {
    RowItem row = new RowItem();
    row.setHeading(heading);
    row.setSubHeading(subHeading);
    row.setSmallImageName(smallImg);
    row.setBigImageName(bigImg);
    arrayList.add(row);
  }
  ////////////////////////////////////////////


  /* Opens new activity when pressed
  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
      FloatingActionButton fab = getView().findViewById(R.id.fab);
      fab.setOnClickListener(lambView -> {
          Intent intent = new Intent(getContext(), AccountSettings.class);
          startActivity(intent);
      });
      myDialog = new Dialog(getContext());
  }
  ////////////////////////////////////////////







  */
  /* Stuff in this function will be executed when the View is already created.
   *  In this case we handle the floating button which creates the popup window which
   *  adds elements to the main list. In this case it's a good question why not to add this body
   *  to onCreateView? it's the same in this case, it's not a heavy task. */
  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    FloatingActionButton fab = getView().findViewById(R.id.fab);
    fab.setOnClickListener(lambView -> {
      ShowPopup(view);
    });
    myDialog = new Dialog(getContext(), R.style.CustomDialogTheme);


  }
  ////////////////////////////////////////////


  /* Function that constructs the popup dialog, it assign the contentView from the
   * fragment_mylist_add_popup layout. Then it handles buttons, editTexts and textViews
   * including the list of elements (or recipes) */
  public void ShowPopup(View v) {

    /* This part is dedicated to the dialog and some of its buttons
     * basically sets the content view and handles the closing action. */
    TextView text_close;
    Button btnAdd;
    Button btnClose;
    myDialog.setContentView(R.layout.fragment_mylist_add_popup);
    text_close = (TextView) myDialog.findViewById(R.id.txtclose);
    btnAdd = (Button) myDialog.findViewById(R.id.btnAdd);
    text_close.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        myDialog.dismiss();
      }
    });
    btnClose = (Button) myDialog.findViewById(R.id.btnCancel);
    btnClose.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        myDialog.dismiss();
      }
    });
    /*      myDialog.getWindow().setLayout(350, 200);*/
    myDialog.show();
    ////////////////////////////////////////////


    /* This part will handle the rest of the buttons of the previous part, in this case
     * it handles the add button behavior.   */
    adapter = new CustomAdapter(getContext(), arrayList);
    editTxt = (TextInputLayout) myDialog.findViewById(R.id.name_of_list);
    list = (ListView) getView().findViewById(R.id.main_list);
    btn = (Button) myDialog.findViewById(R.id.btnAdd);
    // Here, you set the data in your ListView
    list.setAdapter(adapter);
    btn.setOnClickListener(view1 -> {
      // this line adds the data of your EditText and puts in your array
      //arrayList.add(editTxt.getText().toString()); // fix this
      fillArrayList(editTxt.getEditText().getText().toString(), "20-02-2021",
          R.drawable.ic_intolerances, R.drawable.ic_intolerances);
      // next thing you have to do is check if your adapter has changed
      adapter.notifyDataSetChanged();
      myDialog.dismiss();
    });
    ////////////////////////////////////////////
  }

  public void ShowRowNewWindow(View v) {
    Intent intent = new Intent(getContext(), AccountSettings.class);
    startActivity(intent);
  }

  ////////////////////////////////////////////
}