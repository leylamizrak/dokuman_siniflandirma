package yzlbsn;

import java.io.*;
import java.util.*;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;

public class Yzlbsn {

    public static String[] strDizi = {"acaba", "ama", "ancak", "artık", "asla", "aslında", "az",
         "bana", "bazen", "bazı", "bazıları", "bazısı", "belki", "ben", "beni", "benim", "beş",
        "bile", "bir", "birçoğu", "birçok", "birçokları", "biri", "birisi", "birkaç", "birkaçı", "birşey",
        "birşeyi", "biz", "bize", "bizi", "bizim", "böyle", "böylece", "bu", "buna", "bunda", "bundan", "bunu",
         "bunun", "burada", "bütün", "çoğu", "çoğuna", "çoğunu", "çok", "çünkü", "da", "daha", "de", "değil", "demek",
        "diğer", "diğeri", "diğerleri", "diye", "dolayı", "elbette", "en", "fakat", "falan", "felan", "filan",
        "gene", "gibi", "hangi", "hangisi", "hani", "hatta", "hem", "henüz", "hep", "hepsi", "hepsine", "hepsini",
        "her", "herkes", "herkese", "herkesi", "hiç", "hiçbiri", "hiçbirine", "hiçbirini", "için", "içinde", "ile", "ise",
        "işte", "kaç", "kadar", "kendi", "kendine", "kendini", "ki", "kim", "kime", "kimi", "kimin", "kimisi", "madem", "mı",
        "mi", "mu", "mü", "nasıl", "ne", "neden", "nedir", "nerde", "nerede", "nereden", "nereye", "nesi", "neyse", "niçin",
        "niye", "ona", "ondan", "onlar", "onlara", "onlardan", "onların", "onu", "onun", "orada", "oysa", "oysaki",
        "öbürü", "ön", "önce", "ötürü", "öyle", "sana", "sen", "senden", "seni", "senin", "siz", "sizden", "size",
        "sizi", "sizin", "son", "sonra", "seobilog", "şayet", "şey", "şimdi", "şöyle", "şu", "şuna", "şunda", "şundan",
        "şunlar", "şunu", "şunun", "tabi", "tamam", "tüm", "tümü", "üzere", "var", "ve", "veya", "veyahut", "ya", "yani", "yerine",
        "yine", "yoksa", "zaten", "zira"
    };

    static HashMap<String, String> ekonomi;
    static HashMap<String, String> magazin;
    static HashMap<String, String> saglik;
    static HashMap<String, String> siyasi;
    static HashMap<String, String> spor;

    static HashMap<String, String> ekonomiTest;
    static HashMap<String, String> magazinTest;
    static HashMap<String, String> saglikTest;
    static HashMap<String, String> siyasiTest;
    static HashMap<String, String> sporTest;

    public static String kesmeAyir(String metin) {
        metin = metin.toLowerCase();

        char[] dizi = metin.toCharArray();

        for (int i = 0; i < dizi.length; i++) {

            if (dizi[i] == '\'' || dizi[i] == '`' || dizi[i] == '‘' || dizi[i] == '’') {

                for (int j = i; j < dizi.length && dizi[j] != ' '; j++) {
                    dizi[j] = ' ';
                }

            }

        }

        metin = String.copyValueOf(dizi);

        return metin;
    }

    public static String noktalamaStopAyir(String metin) {
        ArrayList<String> wordsList = new ArrayList<String>();

        for (String s : metin.split("[\\p{Punct}\\s]+")) {
            wordsList.add(s);
        }

        Set<String> set = new HashSet<String>(Arrays.asList(strDizi));

        String prc = "";

        for (Iterator<String> it = set.iterator(); it.hasNext();) {
            prc = it.next();

            for (int i = 0; i < wordsList.size(); i++) {

                if (prc.equals(wordsList.get(i))) {
                    wordsList.remove(i);
                    i--;
                }
            }

        }

        metin = "";

        for (int i = 0; i < wordsList.size() - 1; i++) {

            metin = metin + wordsList.get(i) + " ";
        }

        metin = metin + wordsList.get(wordsList.size() - 1);

        //System.out.println(metin);
        //System.out.println("\n\n\n\n\n\n");
        return metin;
    }
    
    

    public static String ZemberekIslem(String metin) {
        Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());

        String kopya = new String();
        String kelime = new String();
        char harf;

        for (int i = 0; i < metin.length(); i++) {
            harf = metin.charAt(i);

            if (harf != ' ' && (i + 1) != metin.length()) {
                kelime = kelime + harf;

                //System.out.println(kelime+" --> 0");  
            } else {
                if ((i + 1) == metin.length()) {
                    kelime = kelime + harf;
                }

                if (zemberek.kelimeDenetle(kelime)) {
                    kopya = kopya + kelime;
                    //System.out.print("1 --> ");
                } else {
                    String[] stringDizisi = zemberek.oner(kelime);
                    if (stringDizisi.length != 0) {
                        kopya = kopya + stringDizisi[0];
                        //kelime=stringDizisi[0];
                        //System.out.print("2 --> ");
                        //System.out.println(stringDizisi[0]);
                    } else {
                        kopya = kopya + kelime;
                        //System.out.print("3 --> ");
                    }
                }

                //System.out.println(kelime);
                kelime = "";
                if ((i + 1) != metin.length()) {
                    kopya = kopya + harf;
                }
            }

            //kopya=kopya+harf;
        }

        //System.out.println("kopya --> "+kopya);
        metin = kopya.toLowerCase();

        //System.out.println("metin --> "+metin);
//System.out.println(metin);
        return metin;
    }

    public static void kelimeIslem(String kategori) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        HashMap<String, String> kategoriAl = new HashMap<String, String>();
        HashMap<String, String> testAl = new HashMap<String, String>();

        File file = new File("1150haber/" + kategori + "/");
        File[] fFosyalar = file.listFiles();

        int egitim = (int) ((fFosyalar.length / 4.0) * 3);

        for (int i = 0; i < fFosyalar.length; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("1150haber/" + kategori + "/" + fFosyalar[i].getName()), "windows-1254"));
            String satir = reader.readLine();
            String dosyaAl = "";

            id++;

            if (i < egitim) {
                while (satir != null) {
                    dosyaAl = dosyaAl + satir;


                    satir = reader.readLine();
                }
                
            dosyaAl = kesmeAyir(dosyaAl);
            dosyaAl = noktalamaStopAyir(dosyaAl);
            dosyaAl = ZemberekIslem(dosyaAl);
            dosyaAl = govdeYap(dosyaAl);
            kategoriAl.put(fFosyalar[i].getName() + "-" + id, dosyaAl);


            } else {

                while (satir != null) {
                    dosyaAl = dosyaAl + satir;

                    satir = reader.readLine();
                }
                
                
            dosyaAl = kesmeAyir(dosyaAl);
            dosyaAl = noktalamaStopAyir(dosyaAl);
            dosyaAl = ZemberekIslem(dosyaAl);
            dosyaAl = govdeYap(dosyaAl);
            testAl.put(fFosyalar[i].getName() + "-" + id, dosyaAl);

            }


            //System.out.println(fFosyalar[i].getName()+" --> "+dosyaAl);
            ikiGram(dosyaAl, id);
            ucGram(dosyaAl, id);

        }

        if (kategori.equals("ekonomi")) {
            ekonomi = kategoriAl;
            ekonomiTest = testAl;
        } else if (kategori.equals("magazin")) {
            magazin = kategoriAl;
            magazinTest = testAl;
        } else if (kategori.equals("saglik")) {
            saglik = kategoriAl;
            saglikTest = testAl;

        } else if (kategori.equals("siyasi")) {
            siyasi = kategoriAl;
            siyasiTest = testAl;

        } else if (kategori.equals("spor")) {
            spor = kategoriAl;
            sporTest = testAl;
        }

    }

    public static String govdeYap(String metin) {

        Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());

        /*String giris = "vatandaşlıklar";
        System.out.println("Giris:" + giris);*/
        String mtn[] = metin.split(" ");

        String m = "";

        for (String giris : mtn) {

            int hangisi = 0;

            String kok = "";

            if (zemberek.kelimeDenetle(giris)) {
                //System.out.println("length-->"+giris.length());
                if (zemberek.kelimeCozumle(giris).length > 0) {
                    kok = zemberek.kelimeCozumle(giris)[0].kok().icerik();
                } else {
                    kok = giris;
                }
            }

            Kelime gecis = new Kelime();
            Kelime[] cozumler = null;
            //for (String kel : kok) {
            cozumler = zemberek.kelimeCozumle(kok);
            List<Kelime> cozum = Arrays.asList(cozumler);
            if (cozum.size() != 0) {
                gecis = cozum.get(0);
                //System.out.println("gecis :"+gecis);

                String deneme = cozum.get(0).toString();

                //System.out.println("deneme-->!"+deneme+"!");
                deneme = deneme.trim();
                if (deneme.endsWith("ISIM ]")) {
                    //System.out.println("bu cümle isim cümlesidir");
                } else {
                    //System.out.println("fiil cümlesidir bu");
                    hangisi = 1;
                }

            }

            //System.out.println("kok:"+kok);
            String govde = "";

            if (kok.equals(giris)) {
                govde = kok;
                //System.out.println("111111");
            } else if (kok == "") {
                govde = giris;
                //System.out.println("22222222222");
            } else if (!kok.equals(giris)) {

                //System.out.println("333333333");
                //System.out.println("\nayristirma sonuclari:");
                List<String[]> ayrisimlar = zemberek.kelimeAyristir(giris);
                //for (String[] strings : ayrisimlar)
                //System.out.println(Arrays.toString(strings));

                //System.out.println("buyukluk --> "+ayrisimlar.size());
                int boyut = ayrisimlar.size();
                String[] strings = null;

                if (boyut == 1) {
                    strings = ayrisimlar.get(0);
                } else if (boyut > 1) {
                    strings = ayrisimlar.get(1);
                }

                if (boyut != 0) {

                    /*for (String str : strings)
            System.out.print(str+" ");

        System.out.println("\n********");*/
                    int sayac = 0;

                    for (int i = strings.length - 1; i >= 0; i--) {

                        sayac = 0;

                        for (int j = 0; j < cekimEkleri.length; j++) {

                            if (hangisi == 0) {

                                if (strings[i] != null && strings[i].equals(cekimEkleri[j])) {
                                    if (!strings[i].equals(kok)) {
                                        strings[i] = null;
                                        sayac = 1;

                                        break;

                                    }
                                }

                            } else {

                                if (strings[i] != null && strings[i].endsWith(cekimEkleri[j])) {
                                    if (!strings[i].equals(kok)) {
                                        strings[i] = null;
                                        sayac = 1;

                                        break;

                                    }
                                }
                            }

                        }

                        if (sayac == 0) {
                            break;
                        }

                    }
                    for (int j = 0; j < strings.length; j++) {
                        if (strings[j] != null) {
                            govde = govde + strings[j];

//                  //System.out.println("str :"+strings[j]);
                        }
                    }

                    //System.out.println("");
                } else {
                    govde = giris;
                }

            }
            //System.out.println("sonuç:"+govde);

            m = m + govde + " ";

            //System.out.println(govde);
        }

        //System.out.println(m);
        return m;
    }

    public static HashMap<String, Integer> frekanslar = new HashMap<String, Integer>();

    public static HashMap<String, Integer> toplamIkiGram = new HashMap<String, Integer>();

    static String[] dosyalar = new String[5];
    static String[][] test = new String[5][];

    public static String[] cekimEkleri = {"lar", "ler", "i", "yi", "şi", "si", "ni", "ı", "yı", "şı", "sı", "nı", "u", "yu", "şu", "su", "nu", "ü", "yü", "şü", "sü", "nü", "e", "ye", "şe",
        "ne", "a", "ya", "şa", "na", "de", "da", "te", "ta", "den", "dan", "ten", "tan", "ın", "yın", "şın", "sın", "nın", "in", "yin", "şin", "sin", "nin", "un", "yun", "şun", "sun", "nun",
        "ün", "yün", "şün", "sün", "nün", "m", "n", "miz", "imiz", "mız", "ımız", "iniz", "niz", "nız", "ınız", "leri", "ca", "ce", "ça", "çe", "dı", "di", "du", "dü", "tı", "ti", "tu", "tü",
        "mış", "miş", "muş", "müş", "yor", "ıyor", "iyor", "eyor", "uyor", "üyor", "ayor", "eyor", "öyor", "oyor", "ecek", "acak", "eceğ", "acağ", "r", "ar", "er", "ır", "ir", "ur", "ür", "meli", "malı", "se", "sa", "k", "ğ", "niz", "ım", "im", "sın", "ız", "iz", "sınız", "siniz"

    };

    static int id = 0;

    static HashMap<String, Integer> frekans = new HashMap<String, Integer>();

    public static void ikiGram(String metin, int id) {
        for (int i = 0; i < metin.length() - 1; i++) {
            char ilk = metin.charAt(i);
            char ikinci = metin.charAt(i + 1);
            String gram = "" + ilk + ikinci;

            gram = gram + "-" + id;

            if (frekans.containsKey(gram)) {
                frekans.put(gram, frekans.get(gram) + 1);

            } else {
                frekans.put(gram, 2);
            }
            System.out.println(gram);

        }

    }

    public static void ucGram(String metin, int id) {
        for (int i = 0; i < metin.length() - 2; i++) {
            char ilk = metin.charAt(i);
            char ikinci = metin.charAt(i + 1);
            char ucuncu = metin.charAt(i + 2);
            String gram = "" + ilk + ikinci + ucuncu;

            gram = gram + "-" + id;

            if (frekans.containsKey(gram)) {
                frekans.put(gram, frekans.get(gram) + 1);

            } else {
                frekans.put(gram, 2);
            }

        }

    }

    static HashMap<String, Integer> toplamFrekans = new HashMap<String, Integer>();

    static HashMap<String, String> ekonomiKume = new HashMap<String, String>();
    static HashMap<String, String> magazinKume = new HashMap<String, String>();
    static HashMap<String, String> saglikKume = new HashMap<String, String>();
    static HashMap<String, String> siyasiKume = new HashMap<String, String>();
    static HashMap<String, String> sporKume = new HashMap<String, String>();

    public static void kumele(String parca, int id, int deger) {
        //if (id == 1 || id == 2)
        if (id >= 1 && id <= 172) {
            if (ekonomiKume.containsKey(parca)) {
                int sayi = Integer.parseInt(ekonomiKume.get(parca));
                int sayi2 = deger + sayi;

                // System.out.println("sayı-2-->" + sayi2);
                ekonomiKume.put(parca, "" + sayi2);
            } else {
                ekonomiKume.put(parca, "" + deger);

            }

        } //else if (id == 4 || id == 5)
        else if (id >= 231 && id <= 402) {

            if (magazinKume.containsKey(parca)) {
                int sayi = Integer.parseInt(magazinKume.get(parca));
                int sayi2 = deger + sayi;

                // System.out.println("sayı-2-->" + sayi2);
                magazinKume.put(parca, "" + sayi2);
            } else {
                magazinKume.put(parca, "" + deger);

            }

        } //else if (id == 7 || id == 8)
        else if (id >= 461 && id <= 632) {
            if (saglikKume.containsKey(parca)) {
                int sayi = Integer.parseInt(saglikKume.get(parca));
                int sayi2 = deger + sayi;

                // System.out.println("sayı-2-->" + sayi2);
                saglikKume.put(parca, "" + sayi2);
            } else {
                saglikKume.put(parca, "" + deger);

            }
        } //else if (id == 10 || id == 11)
        else if (id >= 691 && id <= 862) {

            if (siyasiKume.containsKey(parca)) {
                int sayi = Integer.parseInt(siyasiKume.get(parca));
                int sayi2 = deger + sayi;

                // System.out.println("sayı-2-->" + sayi2);
                siyasiKume.put(parca, "" + sayi2);
            } else {
                siyasiKume.put(parca, "" + deger);

            }
        } //else if (id == 13 || id == 14)
        else if (id >= 921 && id <= 1092) {
            if (sporKume.containsKey(parca)) {
                int sayi = Integer.parseInt(sporKume.get(parca));
                int sayi2 = deger + sayi;

                // System.out.println("sayı-2-->" + sayi2);
                sporKume.put(parca, "" + sayi2);
            } else {
                sporKume.put(parca, "" + deger);

            }

        }

    }

    public static void ortalamaVaryans() {
        //ekonomi için

        for (Map.Entry<String, String> entry3 : ekonomiKume.entrySet()) {

            //System.out.println(entry3.getKey() + " --> " + entry3.getValue());
            float ortalama = Float.parseFloat(entry3.getValue()) / ekonomi.size();

            ekonomiKume.put(entry3.getKey(), "" + ortalama);
            //System.out.println("ortalama ekonomi: "+ortalama);

            //System.out.println("Değer: " + entry3.getValue());
        }

        //System.out.println("varyans-ekonomi");
        for (Map.Entry<String, String> entry3 : ekonomiKume.entrySet()) {

            double toplam = 0.0;

            for (int i = 1; i <= 172; i++) {

                String str = entry3.getKey() + "-" + i;

                if (frekans.containsKey(str)) {

                    double varyans = Math.pow(frekans.get(str) - Float.parseFloat(entry3.getValue()), 2);

                    toplam = varyans + toplam;
                    //System.out.println("toplam:" + toplam);
                }

                frekans.remove(str);

            }

            toplam = toplam / (ekonomiKume.size() - 1);

            ekonomiKume.put(entry3.getKey(), entry3.getValue() + ";" + toplam);
            //System.out.println("varyans:" + toplam);

        }

        //magazin için
        //System.out.println("********");
        for (Map.Entry<String, String> entry3 : magazinKume.entrySet()) {

            //System.out.println(entry3.getKey() + " --> " + entry3.getValue());
            float ortalama = Float.parseFloat(entry3.getValue()) / magazin.size();

            magazinKume.put(entry3.getKey(), "" + ortalama);
            // System.out.println("ortalama magazin: "+ortalama);

            //System.out.println("Değer: " + entry3.getValue());
        }

        //System.out.println("varyans-magazin");
        //*********************
        for (Map.Entry<String, String> entry3 : magazinKume.entrySet()) {

            double toplam = 0.0;

            for (int i = 231; i <= 402; i++) {//Şİmdilik

                String str = entry3.getKey() + "-" + i;

                if (frekans.containsKey(str)) {

                    double varyans = Math.pow(frekans.get(str) - Float.parseFloat(entry3.getValue()), 2);

                    toplam = varyans + toplam;
                    // System.out.println("toplam:" + toplam);
                }

                frekans.remove(str);

            }

            toplam = toplam / (magazinKume.size() - 1);
            //System.out.println("varyans:" + toplam);

            magazinKume.put(entry3.getKey(), entry3.getValue() + ";" + toplam);

        }

        //siyasi için
        //System.out.println("********");
        for (Map.Entry<String, String> entry3 : siyasiKume.entrySet()) {

            //System.out.println(entry3.getKey() + " --> " + entry3.getValue());
            float ortalama = Float.parseFloat(entry3.getValue()) / siyasi.size();

            siyasiKume.put(entry3.getKey(), "" + ortalama);
            //System.out.println("ortalama siyasi: "+ortalama);

            //System.out.println("Değer: " + entry3.getValue());
        }

        // System.out.println("varyans-siyasi");
        //*********************
        for (Map.Entry<String, String> entry3 : siyasiKume.entrySet()) {

            double toplam = 0.0;

            for (int i = 691; i <= 862; i++) {//Şİmdilik

                String str = entry3.getKey() + "-" + i;

                if (frekans.containsKey(str)) {

                    double varyans = Math.pow(frekans.get(str) - Float.parseFloat(entry3.getValue()), 2);

                    toplam = varyans + toplam;
                    //System.out.println("toplam:" + toplam);
                }

                frekans.remove(str);

            }

            toplam = toplam / (siyasiKume.size() - 1);

            siyasiKume.put(entry3.getKey(), entry3.getValue() + ";" + toplam);

        }

        //saglik için
        //System.out.println("********");
        for (Map.Entry<String, String> entry3 : saglikKume.entrySet()) {

            //System.out.println(entry3.getKey() + " --> " + entry3.getValue());
            float ortalama = Float.parseFloat(entry3.getValue()) / saglik.size();

            saglikKume.put(entry3.getKey(), "" + ortalama);
            // System.out.println("ortalama saglik: "+ortalama);
            //System.out.println("Değer: " + entry3.getValue());
        }

        // System.out.println("varyans-saglik");
        //*********************
        for (Map.Entry<String, String> entry3 : saglikKume.entrySet()) {

            double toplam = 0.0;

            for (int i = 461; i <= 632; i++) {//şimdilik

                String str = entry3.getKey() + "-" + i;

                if (frekans.containsKey(str)) {

                    double varyans = Math.pow(frekans.get(str) - Float.parseFloat(entry3.getValue()), 2);

                    toplam = varyans + toplam;
                    //System.out.println("toplam:"+toplam);
                }

                frekans.remove(str);

            }

            toplam = toplam / (saglikKume.size() - 1);

            saglikKume.put(entry3.getKey(), entry3.getValue() + ";" + toplam);

        }

        //spor için
        //System.out.println("********");
        for (Map.Entry<String, String> entry3 : sporKume.entrySet()) {

            //System.out.println(entry3.getKey() + " --> " + entry3.getValue());
            float ortalama = Float.parseFloat(entry3.getValue()) / spor.size();

            sporKume.put(entry3.getKey(), "" + ortalama);
            ///System.out.println("ortalama spor: "+ortalama);

            //System.out.println("Değer: " + entry3.getValue());
        }

        //System.out.println("varyans-spor");
        //*********************
        for (Map.Entry<String, String> entry3 : sporKume.entrySet()) {

            double toplam = 0.0;

            for (int i = 921; i <= 1092; i++) {//Şİmdilik

                String str = entry3.getKey() + "-" + i;

                if (frekans.containsKey(str)) {

                    double varyans = Math.pow(frekans.get(str) - Float.parseFloat(entry3.getValue()), 2);

                    toplam = varyans + toplam;
                    //  System.out.println("toplam:"+toplam);
                }

                frekans.remove(str);

            }

            toplam = toplam / (sporKume.size() - 1);

            sporKume.put(entry3.getKey(), entry3.getValue() + ";" + toplam);

        }

    }

    public static void main(String[] args) throws UnsupportedEncodingException, IOException {

        kelimeIslem("ekonomi");
        System.out.println("\n\n\n" + ekonomi.size() + "\n\n\n");
        kelimeIslem("magazin");
        System.out.println("\n\n\n" + magazin.size() + "\n\n\n");
        kelimeIslem("saglik");
        System.out.println("\n\n\n" + saglik.size() + "\n\n\n");
        kelimeIslem("siyasi");
        System.out.println("\n\n\n" + siyasi.size() + "\n\n\n");
        kelimeIslem("spor");
        System.out.println("\n\n\n" + spor.size() + "\n\n\n");

        int size = ekonomi.size() + magazin.size() + saglik.size() + siyasi.size() + spor.size();
        int size2 = ekonomiTest.size() + magazinTest.size() + saglikTest.size() + siyasiTest.size() + sporTest.size();
        int size3 = size + size2;
        //System.out.println("toplam size"+size3);

        /* for (Map.Entry<String, Integer> entry : frekans.entrySet()) {

            System.out.println(entry.getKey() + " --> " + entry.getValue());
        }
         */
        for (Map.Entry<String, Integer> entry : frekans.entrySet()) {

            String[] parcala = entry.getKey().split("-");

            if (toplamFrekans.containsKey(parcala[0])) {

                toplamFrekans.put(parcala[0], toplamFrekans.get(parcala[0]) + entry.getValue());

            } else {
                toplamFrekans.put(parcala[0], entry.getValue());
            }

        }

        Iterator<Map.Entry<String, Integer>> iter = toplamFrekans.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Integer> entry = iter.next();
            if (entry.getValue() <50) {
                iter.remove();
            }
        }

        iter = frekans.entrySet().iterator();

        //System.out.println("********");
        while (iter.hasNext()) {
            Map.Entry<String, Integer> entry = iter.next();
            String[] parcala = entry.getKey().split("-");

            if (!toplamFrekans.containsKey(parcala[0])) {
                iter.remove();
            }

        }

        //System.out.println("Silme Sonrası");

        /*for (Map.Entry<String, Integer> entry3 : frekans.entrySet()) {

            System.out.println(entry3.getKey() + " --> " + entry3.getValue());

        }*/
        //System.out.println("*******************");
        for (Map.Entry<String, Integer> entry : frekans.entrySet()) {

            String[] parcala = entry.getKey().split("-");
            int id = Integer.parseInt(parcala[1]);
            kumele(parcala[0], id, entry.getValue());

        }

        /*System.out.println("ekonomi:"+ekonomiKume.size());

        System.out.println("magazin:"+magazinKume.size());
        
        System.out.println("saglik:"+saglikKume.size());
        
        System.out.println("siyasi:"+siyasiKume.size());
        
        System.out.println("spor:"+sporKume.size());  */
        ortalamaVaryans();

        /* System.out.println("SİLME SONRASI");

        for (Map.Entry<String, Integer> entry : frekans.entrySet()) {

            System.out.println(entry.getKey() + " --> " + entry.getValue());
        }*/
        int bas = 173, son = 230;
        //int bas=3, son=3;
        HashMap<String, String> siniflandirma = new HashMap<String, String>();


        for (int i = bas; i <= son; i++) {
            //System.out.println("i"+i);
            double[] olasilik = new double[5];
            olasilik[0] = olasilik[1] = olasilik[2] = olasilik[3] = olasilik[4] = 1.0;

            for (Map.Entry<String, Integer> entry : frekans.entrySet()) {

                String[] parcala = entry.getKey().split("-");
                int deger = entry.getValue();
                if (parcala[1].equals(i + "")) {
                    //System.out.println("gram :" + parcala[0]);
                    String varort;
                    String ayır[];
                    double toplam = 1.0, toplam2 = 1.0;
                    if (ekonomiKume.containsKey(parcala[0])) {
                        varort = ekonomiKume.get(parcala[0]);
                        //System.out.println("sd:"+varort);
                        ayır = varort.split(";");
                        if (Double.parseDouble(ayır[0]) != 0.0 && Double.parseDouble(ayır[1]) != 0.0) {
                            toplam = Math.sqrt(2 * 3.14 * Double.parseDouble(ayır[1])* Double.parseDouble(ayır[1]));
                            toplam2 = -1 * Math.pow(deger - Double.parseDouble(ayır[0]), 2) / (2.0 * Double.parseDouble(ayır[1])* Double.parseDouble(ayır[1]));
                            toplam2 = Math.exp(toplam2);
                            // System.out.println("toplam"+toplam+"toplam2"+toplam2);
                            if (toplam2 == 0.0) {
                                toplam2 = 0.00000000000001;
                            }
                            toplam = (1 / toplam) * toplam2;
                            //System.out.println("toplammm" + toplam2);
                            toplam = Math.log(toplam);3
                            olasilik[0] = olasilik[0] + toplam;
                            // }

                        }
                    }
                    if (magazinKume.containsKey(parcala[0])) {
                        varort = magazinKume.get(parcala[0]);
                        //System.out.println("sd:"+varort);
                        ayır = varort.split(";");
                        if (Double.parseDouble(ayır[0]) != 0.0 && Double.parseDouble(ayır[1]) != 0.0) {

                            toplam = Math.sqrt(2 * 3.14 * Double.parseDouble(ayır[1])* Double.parseDouble(ayır[1]));
                            toplam2 = -1 * Math.pow(deger - Double.parseDouble(ayır[0]), 2) / (2 * Double.parseDouble(ayır[1])* Double.parseDouble(ayır[1]));
                            toplam2 = Math.exp(toplam2);
                            if (toplam2 == 0.0) {
                                toplam2 = 0.00000000000001;
                            }
                            toplam = (1 / toplam) * toplam2;
                            toplam = Math.log(toplam);
                            //toplam=Math.log(toplam);
                            olasilik[1] = olasilik[1] + toplam;

                        }
                    }
                    if (saglikKume.containsKey(parcala[0])) {
                        varort = saglikKume.get(parcala[0]);
                        //System.out.println("sd:"+varort);
                        ayır = varort.split(";");
                        if (Double.parseDouble(ayır[0]) != 0.0 && Double.parseDouble(ayır[1]) != 0.0) {

                            toplam = Math.sqrt(2 * 3.14 * Double.parseDouble(ayır[1])* Double.parseDouble(ayır[1]));
                            toplam2 = -1 * Math.pow(deger - Double.parseDouble(ayır[0]), 2) / (2 * Double.parseDouble(ayır[1])* Double.parseDouble(ayır[1]));
                            toplam2 = Math.exp(toplam2);
                            if (toplam2 == 0.0) {
                                toplam2 = 0.00000000000001;
                            }
                            toplam = (1 / toplam) * toplam2;
                            toplam = Math.log(toplam);
                            olasilik[2] = olasilik[2] + toplam;

                        }
                    }
                    if (siyasiKume.containsKey(parcala[0])) {
                        varort = siyasiKume.get(parcala[0]);
                        //System.out.println("sd:"+varort);
                        ayır = varort.split(";");
                        if (Double.parseDouble(ayır[0]) != 0.0 && Double.parseDouble(ayır[1]) != 0.0) {

                            toplam = Math.sqrt(2 * 3.14 * Double.parseDouble(ayır[1])* Double.parseDouble(ayır[1]) );
                            toplam2 = -1 * Math.pow(deger - Double.parseDouble(ayır[0]), 2) / (2 * Double.parseDouble(ayır[1])* Double.parseDouble(ayır[1]));
                            toplam2 = Math.exp(toplam2);
                            if (toplam2 == 0.0) {
                                toplam2 = 0.00000000000001;
                            }
                            toplam = (1 / toplam) * toplam2;
                            toplam = Math.log(toplam);
                            olasilik[3] = olasilik[3] + toplam;

                        }
                    }
                    if (sporKume.containsKey(parcala[0])) {
                        varort = sporKume.get(parcala[0]);
                        //System.out.println("sd:"+varort);
                        ayır = varort.split(";");
                        if (Double.parseDouble(ayır[0]) != 0.0 && Double.parseDouble(ayır[1]) != 0.0) {
                            toplam = Math.sqrt(2 * 3.14 * Double.parseDouble(ayır[1])* Double.parseDouble(ayır[1]));
                            toplam2 = -1 * Math.pow(deger - Double.parseDouble(ayır[0]), 2) / (2 * Double.parseDouble(ayır[1])* Double.parseDouble(ayır[1]));
                            toplam2 = Math.exp(toplam2);
                            if (toplam2 == 0.0) {
                                toplam2 = 0.00000000000001;
                            }
                            toplam = (1 / toplam) * toplam2;
                            toplam = Math.log(toplam);
                            olasilik[4] = olasilik[4] + toplam;

                        }
                    }

                }

            }

            olasilik[0] = -1*olasilik[0] * 0.2;
            olasilik[1] = -1*olasilik[1] * 0.2;
            olasilik[2] = -1*olasilik[2] * 0.2;
            olasilik[3] = -1*olasilik[3] * 0.2;
            olasilik[4] = -1*olasilik[4] * 0.2;

            //System.out.println("olasilik: " + olasilik[0] + "**" + olasilik[1] + "**" + olasilik[2] + "**" + olasilik[3] + "**" + olasilik[4]);

            //System.out.println("");
            String kategori = "ekonomi";
            double max = olasilik[0];
            if (olasilik[1] > max) {
                max = olasilik[1];
                kategori = "magazin";
            }
            if (olasilik[2] > max) {
                max = olasilik[2];
                kategori = "saglik";
            }
            if (olasilik[3] > max) {
                max = olasilik[3];
                kategori = "siyasi";
            }
            if (olasilik[4] > max) {
                max = olasilik[4];
                kategori = "spor";
            }


            siniflandirma.put(i + "", kategori);
            olasilik[0] = olasilik[1] = olasilik[2] = olasilik[3] = olasilik[4] = 1;

            /* if(i==3)
         {
             i=5; son=6;
         }            
         else if(i==6)
         {
             i=8; son=9;

         }
         else if(i==9)
         {
            i=11; son=12;
         }
         else if(i==12)
         {
            i=14; son=15;
         }   
            
             */
            if (i == 230) {
                //System.out.println("MAGAZİN");
                i = 402;
                son = 460;
            } else if (i == 460) {
                //System.out.println("SAĞLIK");
                i = 632;
                son = 690;

            } else if (i == 690) {
                //System.out.println("SİYASİ");
                i = 862;
                son = 920;
            } else if (i == 920) {
                //System.out.println("SPOR");
                i = 1092;
                son = 1150;
            }

        }

        int[][] degerler = new int[6][5];

        System.out.println("SINIFLANDIRMA");

        for (Map.Entry<String, String> entry : siniflandirma.entrySet()) {

            System.out.println(entry.getKey() + " --> " + entry.getValue());
            int id = Integer.parseInt(entry.getKey());

            int indeks1=0;

            if (id >= 173 && id <= 230) {
                indeks1 = 0;
            } else if (id >= 403 && id <= 460) {
                indeks1 = 1;

            } else if (id >= 633 && id <= 690) {
                indeks1 = 2;

            } else if (id >= 863 && id <= 920) {
                indeks1 = 3;

            } else if (id >= 1093 && id <= 1150) {
                indeks1 = 4;

            }
            
            int indeks2=0;
            
            if(entry.getValue().equals("ekonomi"))
            {
             indeks2=0;   
            }
            else if(entry.getValue().equals("magazin"))
            {
                indeks2=1;
            }
             else if(entry.getValue().equals("saglik"))
            {
               indeks2=2;
            }
             else if(entry.getValue().equals("siyasi"))
            {
               indeks2=3;
            }
             else if(entry.getValue().equals("spor"))
            {
              indeks2=4; 
            }
            
            
        degerler[indeks1][indeks2]+=1;    
        degerler[5][indeks2]+=1;

        }
        System.out.println("degerler");
        for(int i=0;i<6;i++)
        {
            for(int j=0;j<5;j++)
                System.out.print(degerler[i][j]+" ");
            System.out.println("");
            
        }
      
        
        double[] recall=new double[5];
        double[] precision=new double[5];
        
        String[] siniflar={"ekonomi","magazin","saglik","siyasi","spor"};
        System.out.println("RECALL DEGERLERİ");
        
        for(int i=0;i<5;i++)
        {
            System.out.println(siniflar[i]+" --> "+(degerler[i][i]/58.0));
            recall[i]= degerler[i][i]/172.0;
           
        }
         System.out.println("PRECİSİON DEGERLERİ");
        
        for(int i=0;i<5;i++)
        {
            System.out.println(siniflar[i]+" --> "+((double)degerler[i][i]/degerler[5][i]));
            precision[i]=(double)degerler[i][i]/degerler[5][i];
        }
         System.out.println("F-MEASURE");
        for(int i=0;i<5;i++)
        {
         System.out.println(siniflar[i]+" --> "+ (2*((precision[i]*recall[i])/(precision[i]+recall[i]))));
        }
        
        
        /*  System.out.println("VARYANS-SONUCU");
        
         for (Map.Entry<String, String> entry3 : ekonomiKume.entrySet()) {

            System.out.println(entry3.getKey() + " --> " + entry3.getValue());

        }
        
         System.out.println("DENEME");
         
          for (Map.Entry<String, Integer> entry3 : frekans.entrySet()) {
            System.out.println(entry3.getKey() + " --> " + entry3.getValue());
        }
        
          //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
          
            for (Map.Entry<String, Integer> entry3 : frekans.entrySet()) {
                
            System.out.println(entry3.getKey() + " --> " + entry3.getValue());
            
            
        }*/
    }

}
