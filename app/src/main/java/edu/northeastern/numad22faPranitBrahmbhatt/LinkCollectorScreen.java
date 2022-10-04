package edu.northeastern.numad22faPranitBrahmbhatt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LinkCollectorScreen extends AppCompatActivity {

    RecyclerView linkRecyclerView;
    RecyclerLinkAdapter linkAdapter;
    FloatingActionButton callAddLinkDialogButton;
    ArrayList<LinkModel> arrLink = new ArrayList<>();
    String linkUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector_screen);

        linkRecyclerView = findViewById(R.id.linkRecycler);
        callAddLinkDialogButton = findViewById(R.id.fabAddLinkDialog);

        callAddLinkDialogButton.setOnClickListener(view -> {
            Dialog addLinkDialog = new Dialog(LinkCollectorScreen.this);
            addLinkDialog.setContentView(R.layout.add_link);

            EditText editLinkName = addLinkDialog.findViewById(R.id.linkName);
            EditText editLink = addLinkDialog.findViewById(R.id.link);
            Button addLinkButton = addLinkDialog.findViewById(R.id.addLink);

            addLinkButton.setOnClickListener(view1 -> {
                String name = "", link = "";
                if(!editLinkName.getText().toString().equals(""))
                {
                    name = editLinkName.getText().toString();
                } else{
                    Snackbar.make(LinkCollectorScreen.this, view1,
                            "Please add Link Name", Snackbar.LENGTH_LONG).show();
                }
                if(!editLink.getText().toString().equals(""))
                {
                    link = editLink.getText().toString();
                } else{
                    Snackbar.make(LinkCollectorScreen.this, view1,
                            "Please add a Link", Snackbar.LENGTH_LONG).show();
                }

                arrLink.add(new LinkModel(name, link));

                linkAdapter.notifyItemInserted(arrLink.size()-1);

                linkRecyclerView.scrollToPosition(arrLink.size()-1);

                addLinkDialog.dismiss();
            });

            addLinkDialog.show();

            Snackbar.make(LinkCollectorScreen.this, view,
                    "Link Successfully added", Snackbar.LENGTH_LONG).show();
        });

        linkRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        linkAdapter = new RecyclerLinkAdapter (this, arrLink);
        linkRecyclerView.setAdapter(linkAdapter);
    }
}