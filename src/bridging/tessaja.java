/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

/**
 *
 * @author khanzasoft
 */
public class tessaja {
    public static ApiBPJS api=new ApiBPJS();
    public static void tessaja(){
        System.out.println("X-Time : "+String.valueOf(api.GetUTCdatetimeAsString()));
        System.out.println("Signature : "+api.getHmac());
    }
    public static void main(String[] args) {
        tessaja();
        BPJSCekNIK ck=new BPJSCekNIK();
        ck.tampil("3271040907830028");
    }
    
}
