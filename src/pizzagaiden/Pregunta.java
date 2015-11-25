package pizzagaiden;

import java.util.ArrayList;

public class Pregunta
{
    int iType;
    String sPregunta, sRespuesta;

    public Pregunta(String sQst, String sAns) {
        sPregunta= sQst;
        sRespuesta= sAns;
    }
        
    public Pregunta(String str) {
        String[] parts = str.split(",");
        this.iType = Integer.parseInt(parts[0]);
        this.sPregunta = parts[1];
        this.sRespuesta = parts[2];
    }

    public Pregunta() {
    }

    public void setPregunta(String sQst) {
        sPregunta= sQst;
    }

    public void setRespuesta(String sAns) {
        sRespuesta= sAns;
    }

    public String getPregunta() {
        return sPregunta;
    }

    public String getRespuesta() {
        return sRespuesta;
    }

    public int getiType() {
        return iType;
    }

    public void setiType(int iType) {
        this.iType = iType;
    }
    
    @Override
    public String toString() {
        return iType + "," + sPregunta + "," + sRespuesta;
    }
    
    public static ArrayList<ArrayList<Pregunta>> separaTipos(ArrayList<String> arr) {
        ArrayList<ArrayList<Pregunta>> arreglo = new ArrayList<ArrayList<Pregunta>>();
        Pregunta preTmp = null;
        ArrayList<Pregunta> arrMate = new ArrayList<>();
        ArrayList<Pregunta> arrIngles = new ArrayList<>();
        for(String str : arr) {
            preTmp = new Pregunta(str);
            if(preTmp.iType == 1) {
                arrIngles.add(preTmp);
            }
            else {
                arrMate.add(preTmp);
            }
        }
        arreglo.add(arrMate);
        arreglo.add(arrIngles);
        
        return arreglo;
    }
}