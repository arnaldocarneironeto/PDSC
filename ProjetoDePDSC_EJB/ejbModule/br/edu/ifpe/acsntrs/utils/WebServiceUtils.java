package br.edu.ifpe.acsntrs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
public class WebServiceUtils
{
    public static Triple<String, String, String> getRandomTripleNameEmailGender()
    {
        String name = "";
        String email = "";
        String gender = "";
        try
        {
            URL siteURL = new URL("http://www.fakenamegenerator.com/gen-random-br-br.php");
            URLConnection siteConnection = siteURL.openConnection();
            siteConnection.setConnectTimeout(250);
            siteConnection.setReadTimeout(250);
            siteConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            boolean nextIsName = false;
            try (final BufferedReader in = new BufferedReader(new InputStreamReader(siteConnection.getInputStream(), "UTF-8")))
            {
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                {
                    if (nextIsName)
                    {
                        name = inputLine.substring(inputLine.indexOf("<h3>") + 4, inputLine.indexOf("</h3>"));
                    }
                    if (inputLine.contains("@"))
                    {
                        email = inputLine.substring(inputLine.indexOf("<dd>") + 4);
                        email = email.substring(0, email.indexOf(" "));
                    }
                    if (inputLine.contains("alt=\"Female\"")) gender = "f";
                    if (inputLine.contains("alt=\"Male\"")) gender = "m";
                    nextIsName = inputLine.contains("<div class=\"address\">");
                }
            }
        }
        catch (MalformedURLException ex)
        {
            name = "";
            email = "";
        }
        catch (IOException ex)
        {
            name = "";
            email = "";
        }
        return Triple.of(name, email, gender);
    }
}