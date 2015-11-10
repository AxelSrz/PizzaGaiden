package pizzagaiden.Pizzarama;

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
  
  void setRespuesta(String sAns)
  {
    sRespuesta= sAns;
  }
  
  String getPregunta()
  {
    return sPregunta;
  }
  
  String getRespuesta()
  {
    return sRespuesta;
  }
}