package hkust.comp3111h.focus.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import hkust.comp3111h.focus.R;
import hkust.comp3111h.focus.database.TaskItem;

/**
 * Base class for all popup setting dialogs
 * Provide the interface and common functions for extension
 */

public abstract class PopupControlSet extends TaskEditControlSet {
  protected final View displayView;
  protected Dialog dialog;
  protected final TextView displayText;
  private final String titleString;

  final DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
    @Override 
    public void onClick(DialogInterface d, int which){
      onOkClick();
    }
  };
  final DialogInterface.OnCancelListener cancelListener = new DialogInterface.OnCancelListener() {
    @Override
    public void onCancel(DialogInterface d) {
      onCancelClick();
    }
  };
  public PopupControlSet(Activity activity, int viewLayout, int displayViewLayout, final int title) {
    super(activity, viewLayout);
    if(displayViewLayout != -1) {
      this.displayView = LayoutInflater.from(activity).inflate(displayViewLayout, null);
      displayText =(TextView) displayView.findViewById(R.id.display_row_title);
      if(displayText != null) {
        displayText.setMaxLines(2);
      }
    } else {
      this.displayView = null;
      this.displayText = null;
    }
    titleString  = (title > 0) ? activity.getString(title):"";
    if(displayView!=null) {
      displayView.setOnClickListener(getDisplayClickListener());
    }
  }
  @Override
  public View getDisplayView() {
    return displayView;
  }

  protected Dialog buildDialog(String title, final DialogInterface.OnClickListener okClickListener, DialogInterface.OnCancelListener cancelClickListener) {
    dialog = new Dialog(activity);
    if(title.length() == 0) {
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    } else {
      dialog.setTitle(title);
    }
    View v =  getView();

    dialog.setContentView(v, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
    Button dismiss = (Button) v.findViewById(R.id.edit_dlg_ok);
    if(dismiss!=null) {
      dismiss.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          okClickListener.onClick(dialog,0);
          dialog.dismiss();
        }
      });
    }
    LayoutParams params = dialog.getWindow().getAttributes();
    params.width = LayoutParams.FILL_PARENT;
    params.height = LayoutParams.WRAP_CONTENT;
    Configuration config = activity.getResources().getConfiguration();
    int size = config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    dialog.setOnCancelListener(cancelClickListener);
    dialog.setOwnerActivity(PopupControlSet.this.activity);
    return dialog;
  }
  protected OnClickListener getDisplayClickListener() {
    return new OnClickListener() {
      @Override
      public void onClick(View v) {
        if(dialog == null) {
          buildDialog(titleString, okListener, cancelListener);
        }
        dialog.show();
      }
    };
  }
  protected void onOkClick() {
    refreshDisplayView();
  }
  protected void onCancelClick(){
    refreshDisplayView();
  }
  @Override 
  public void initTask(TaskItem task) {
    super.initTask(task);
    refreshDisplayView();
  }
  protected abstract void refreshDisplayView();
}

