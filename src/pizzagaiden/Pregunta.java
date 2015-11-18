package pizzagaiden;

public class Pregunta
{
  String sPregunta, sRespuesta;
  
  public Pregunta(String sQst, String sAns)
  {
    sPregunta= sQst;
    sRespuesta= sAns;
  }
  
  public void setPregunta(String sQst)
  {
    sPregunta= sQst;
  }
  
  public void setRespuesta(String sAns)
  {
    sRespuesta= sAns;
  }
  
  public String getPregunta()
  {
    return sPregunta;
  }
  
  public String getRespuesta()
  {
    return sRespuesta;
  }
}