package com.example.workouttimer;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DialogActivity extends AppCompatActivity {
    Button btnOpenDialog, alertDialog, btnPopupMenu, btnPopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogsbutton);

        btnOpenDialog = findViewById(R.id.btnOpenDialog);
        alertDialog = findViewById(R.id.alertDialog);
        btnPopupMenu = findViewById(R.id.btnPopupMenu);
        btnPopupWindow = findViewById(R.id.btnPopupWindow);
        alertDialog.setOnClickListener(view -> showAlertDialog());
        btnOpenDialog.setOnClickListener(view -> showCustomDialog());

        btnPopupMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(DialogActivity.this, v);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.item_edit:
                        Toast.makeText(DialogActivity.this, "Edit clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item_delete:
                        Toast.makeText(DialogActivity.this, "Delete clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            });

            popupMenu.show();
        });

        //if this android.nonFinalResIds=false is not used in gradle.properties

//        popupMenu.setOnMenuItemClickListener(item -> {
//            int id = item.getItemId();
//            if (id == R.id.item_edit) {
//                Toast.makeText(DialogActivity.this, "Edit clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            } else if (id == R.id.item_delete) {
//                Toast.makeText(DialogActivity.this, "Delete clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            } else {
//                return false;
//            }
//        });
//
//        popupMenu.show();
//    });

        btnPopupWindow.setOnClickListener(v -> {
            // Inflate custom layout
            View popupView = getLayoutInflater().inflate(R.layout.activity_popup_layout, null);

            // Create PopupWindow
            PopupWindow popupWindow = new PopupWindow(
                    popupView,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    true // Focusable (dismiss when clicking outside)
            );

            // Find views inside popup
            TextView tvMessage = popupView.findViewById(R.id.tvMessage);
            Button btnClose = popupView.findViewById(R.id.btnClose);

            // Close button action
            btnClose.setOnClickListener(view -> popupWindow.dismiss());

            // Show popup below the button
            popupWindow.showAsDropDown(v, 0, 0);
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item?")
                .setCancelable(false) // Prevent closing when tapping outside (optional)
                .setPositiveButton("Yes", (dialog, which) -> {
                    // ✅ Action when Yes clicked
                    Toast.makeText(this, "Item deleted!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss(); // ✅ close dialog
                });

        // Create & show dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void showCustomDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialogs);
        dialog.setCancelable(false); // prevent dismiss on outside touch

        // Find views inside dialog
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        EditText etInput = dialog.findViewById(R.id.etInput);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        // Cancel button
        btnCancel.setOnClickListener(view -> dialog.dismiss());

        // OK button
        btnOk.setOnClickListener(view -> {
            String input = etInput.getText().toString().trim();
            Toast.makeText(this, "You entered: " + input, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }
}