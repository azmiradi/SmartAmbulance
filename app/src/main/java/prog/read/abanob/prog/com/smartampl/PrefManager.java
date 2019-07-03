package prog.read.abanob.prog.com.smartampl;

import android.content.Context;
import android.content.SharedPreferences;

 import prog.read.abanob.prog.com.smartampl.model.User;

public class PrefManager {
     Context context;

    public PrefManager(Context context) {
        this.context = context;
    }

    public void saveLoginDetails(User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", user.getEmail());
        editor.putString("Name", user.getName());
        editor.putString("AccessToken", user.getAccessToken());
        editor.putInt("Id", 0);

        editor.commit();
    }

    public User getBrand(){
        User user=new User();
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        user.setName(sharedPreferences.getString("Name", ""));
         user.setEmail(sharedPreferences.getString("Email", ""));
        user.setAccessToken(sharedPreferences.getString("AccessToken", ""));
        user.setId(sharedPreferences.getInt("Id", 0));

        return user;
    }
    public String getEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
}
    public String getName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Name", "");
    }
    public int getId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("Id", 0);
    }




    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
        return isEmailEmpty ;
    }

    public void LogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

     }

}