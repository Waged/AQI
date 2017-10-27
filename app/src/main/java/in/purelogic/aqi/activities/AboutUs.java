package in.purelogic.aqi.activities;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.purelogic.aqi.R;

public class AboutUs extends AppCompatActivity {

    @BindView(R.id.about_us_title)
    TextView tvAboutUsTitle;

    @BindView(R.id.about_us_sub_title1)
    TextView tvTitle1;

    @BindView(R.id.about_us_sub_title2)
    TextView tvTitle2;
    @BindView(R.id.about_us_sub_title3)
    TextView tvTitle3;

    @BindView(R.id.btnFacebook)
    ImageButton btnFacebook;


    String url = "https://www.facebook.com/aqiindia/";
    private int onStartCount = 0;

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_right);

        } else if (onStartCount == 1) {
            onStartCount++;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_left);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }

            Typeface tfRobotoBlack = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Black.ttf");
        //Typeface tfRobotoBlackItalic = Typeface.createFromAsset(getAssets(),"fonts/Roboto-BlackItalic.ttf");
        Typeface tfRobotoBold= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        Typeface tfRobotoBoldCondensed = Typeface.createFromAsset(getAssets(),"fonts/Roboto-BoldCondensed.ttf");
       // Typeface tfRobotoBoldItalic = Typeface.createFromAsset(getAssets(),"fonts/Roboto-BoldItalic.ttf");
        tvAboutUsTitle.setTypeface(tfRobotoBoldCondensed);
        tvTitle1.setTypeface(tfRobotoBlack);
        tvTitle2.setTypeface(tfRobotoBold);
        tvTitle3.setTypeface(tfRobotoBoldCondensed);


        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           startActivity(newFacebookIntent(getPackageManager(),url));
            }
        });
    }

    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }
}
