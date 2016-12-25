package nd.com.getoffline;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by DravitLochan on 17-12-2016.
 */

public class PageInfo {

    int id;
    private String name,src_code,url;
    Context context;
    public PageInfo()
    {

        Toast.makeText(context, "Internal error. try again!", Toast.LENGTH_LONG).show();
    }
    public PageInfo(int id, String name, String src_code,String url)
    {
        this.id=id;
        this.name=name;
        this.src_code=src_code;
        this.url=url;
    }

    /*
                                            as of now, these methods are not needed.
    public void setId(int id)
    {
        this.id=id;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public void setSrcCode(String src_code)
    {
        this.src_code=src_code;
    }
    */

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getSrcCode()
    {
        return this.src_code;
    }

    public String getUrl()
    {
        return this.url;
    }
}
