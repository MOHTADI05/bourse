package tn.esprit.mfb.Services;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxyTypeAdapter extends TypeAdapter<Proxy> {
    @Override
    public void write(JsonWriter out, Proxy value) throws IOException {
        out.beginObject(); // Début de l'objet JSON
        out.name("type").value(value.type().name()); // Écriture du type du proxy
        out.name("address").value(value.address().toString()); // Écriture de l'adresse du proxy
        out.endObject(); // Fin de l'objet JSON
    }

    @Override
    public Proxy read(JsonReader in) throws IOException {
        in.beginObject(); // Début de l'objet JSON
        Proxy.Type type = null;
        String addressStr = null;
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("type")) {
                type = Proxy.Type.valueOf(in.nextString()); // Lecture du type du proxy
            } else if (name.equals("address")) {
                addressStr = in.nextString(); // Lecture de l'adresse du proxy
            } else {
                in.skipValue(); // Ignorer les champs inconnus
            }
        }
        in.endObject(); // Fin de l'objet JSON
        // Construction de l'objet Proxy
        InetSocketAddress address = new InetSocketAddress(addressStr.split(":")[0], Integer.parseInt(addressStr.split(":")[1]));
        return new Proxy(type, address);
    }



}
