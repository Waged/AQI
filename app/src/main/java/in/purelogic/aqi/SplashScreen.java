package in.purelogic.aqi;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import in.purelogic.aqi.activities.MainActivity;


public class SplashScreen extends AwesomeSplash {

    //DO NOT OVERRIDE onCreate()!
    //if you need to start some services do it in initSplash()!

    @Override
    public void initSplash(ConfigSplash configSplash) {

			/* you don't have to override every property */
     /*   MediaPlayer mp ;
        mp=MediaPlayer.create(getApplicationContext(),R.raw.liketoysoldier);// the song is a filename which i have pasted inside a folder **raw** created under the **res** folder.//
        mp.start();
    */
        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorPrimaryDark); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_TOP);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP
        //configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);
        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default
        //Customize Logo
        configSplash.setLogoSplash(R.drawable.aqi_splash); //or any other drawable
        //configSplash.setAnimLogoSplashDuration(500); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.DropOut);
        configSplash.setAnimLogoSplashDuration(1500);
        //configSplash.setAnimLogoSplashTechnique(Techniques.BounceInLeft);

        //choose one form Techniques (ref: https://gi.com/daimajia/AndroidViewAnimations)


        //Customize Title
        configSplash.setTitleSplash("Know What You breath !");
        configSplash.setTitleTextColor(R.color.colorPrimary);
        configSplash.setTitleTextSize(32f); //float value
        configSplash.setAnimTitleDuration(950);
        configSplash.setAnimTitleTechnique(Techniques.DropOut);
        configSplash.setTitleFont("fonts/grand_hotel.otf");




    }

    @Override
    public void animationsFinished() {
        Intent intent = new Intent(SplashScreen.this,
                MainActivity.class);

        startActivity(intent);

    }
}



