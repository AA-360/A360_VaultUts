import com.automationanywhere.botcommand.samples.commands.basic.DelKeyCredencial;
import com.automationanywhere.botcommand.samples.commands.basic.GetCredencial;
import com.automationanywhere.botcommand.samples.commands.basic.PutKeyCredencial;
import com.automationanywhere.botcommand.samples.commands.basic.VaultStart;

//import com.automationanywhere.core.security.SecureString;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class SessionTest {
    @Test
    public void teste(){
        Map<String, Object> theMap = new HashMap<String, Object>();

        VaultStart sessObj = new VaultStart();
        GetCredencial gc = new GetCredencial();
        PutKeyCredencial pc = new PutKeyCredencial();
        DelKeyCredencial dc = new DelKeyCredencial();
        sessObj.setSessionMap(theMap);
        gc.setSessionMap(theMap);
        pc.setSessionMap(theMap);
        dc.setSessionMap(theMap);


        sessObj.action2("mysession","<host>","<key>");

//        gc.action("mysession","Google","smtp-rpa");
        pc.action("mysession","Google","smtp-rpa","hehe","foi carai3");
        dc.action("mysession","Google","smtp-rpa","hehe");


        System.out.println("==================");
    }
}