package pizzagaiden;

public class Pregunta
{
    int iType;
    String sPregunta, sRespuesta;

    public Pregunta(String sQst, String sAns) {
        sPregunta= sQst;
        sRespuesta= sAns;
    }
        
    public Pregunta(String str) {
        String[] parts = str.split(" ");
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
        return iType + " " + sPregunta + " " + sRespuesta;
    }
}