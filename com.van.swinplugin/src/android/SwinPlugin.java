import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import android.util.Log;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
 
public class SwinPlugin extends CordovaPlugin {

	private Context context;
	private PackageManager packageManager;

	public static final String TAG = "Swin Plugin";
	public static final String packageName = <应用包名>;
	public static final String downloadSite = <下载链接地址>;
 
	/**
	* Constructor.
	*/
	public SwinPlugin() {}
 
	/**
	* Sets the context of the Command. This can then be used to do things like
	* get file paths associated with the Activity.
	*
	* @param cordova The context of the main Activity.
	* @param webView The CordovaWebView Cordova is running in.
	*/
 
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		packageManager = cordova.getActivity().getPackageManager();
		context = cordova.getActivity();
		Log.v(TAG,"Init SwinPlugin");
	}
 
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		//Start another activity
		cordova.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				if (isInstallApp(context, packageName)) {
					Intent intent = packageManager.getLaunchIntentForPackage(packageName);
					context.startActivity(intent);
				} else {
					//进入Toast提醒未下载
					//Toast toast = Toast.makeText(context, "应用未安装，请先进入安装下载...", Toast.LENGTH_LONG);
					//toast.setGravity(Gravity.CENTER, 0, 0);
					//toast.show();
					dialog();
				}
			}
		});
		return true;
	}
	
	boolean isInstallApp(Context context,String packageName)
    {
        try {
            packageManager.getApplicationInfo(packageName,PackageManager.GET_UNINSTALLED_PACKAGES);  
            return true;
        } catch (NameNotFoundException e) {
            // TODO: handle exception
            return false;
        }
    }
	
	protected void dialog() {
		AlertDialog.Builder builder = new Builder(cordova.getActivity());
		builder.setMessage("确认安装吗？");
		builder.setTitle("检测到您未安装xxx");
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//进入下载链接
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadSite));
				intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
				context.startActivity(intent);
				//关闭界面
				//cordova.getActivity().finish();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
}