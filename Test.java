import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

public class Test {
    public static void main(String[] args) {
        // TODO code application logic here

        ArrayList<Banknot> trainData_AL = new ArrayList<Banknot>();       //train_data içerisindekileri tutmak için ArrayList

        String filePath = "data_banknote_authentication.txt";
        trainData_AL = readFİle(filePath, trainData_AL);       //readFile() dosyayı okuyor ve okuduğu değerlerle nesne oluşturup
        // ArrayListe ekliyor

        Banknot userObject = getInput();   //kullanıcı nesnesi oluşturuluyor
        Scanner klavye = new Scanner(System.in);
        System.out.print("k Değerini Giriniz: ");
        int k = Integer.parseInt(klavye.next());

        int[] kkomsu = new int[k];
        double[] DM = distance(userObject, trainData_AL);     // uzaklık matrisi döndürülüyor
        int tur = turBelirle(DM, k, trainData_AL, kkomsu);    // tür belirleniyor

        userObject.setTur(tur);

        System.out.println();
        System.out.printf("%60s %60s %16s", "ÖZELLİKLER", "TÜR", "DISTANCE");
        System.out.println();

        for(int item:kkomsu){               //k adet komşunun özellikleri yazdırılıyor
            System.out.print(trainData_AL.get(item).toString());
            System.out.format("%15f", DM[item]);
            System.out.println();
        }

        System.out.println();


        System.out.println("Türü Belirlenmek İstenen Banknot: ");
        System.out.println("Tahmin edilen Tür: " + userObject.getTur());
        System.out.println();

        System.out.println();


        ArrayList<Banknot> testData_AL = new ArrayList<Banknot>();     //test data içerisindekileri tutmak için arraylist
        String filePath2 = "data_test.txt";


        testData_AL = readFİle(filePath2, testData_AL);
        System.out.print("k Değerini Giriniz: ");
        int k2 = Integer.parseInt(klavye.next());

        int correctValue = 0;
        int falseValue = 0;

        for (Banknot item:testData_AL){

            int[] kkomsu2 = new int[k2];
            double[] DM2 = distance(item, trainData_AL);    // uzaklık matrisi döndürülüyor
            int gercekTur = item.getTur();
            int tur2 = turBelirle(DM2, k2, trainData_AL, kkomsu2);    //tür belirleniyor

            if(item.getTur() == tur2)
                correctValue ++;
            else
                falseValue ++;

            item.setTur(tur2);

            System.out.println();
            System.out.printf("%60s %60s %16s", "ÖZELLİKLER", "TÜR", "DISTANCE");
            System.out.println();
            for(int item2:kkomsu2){             //k adet komşu yazdırılıyor
                System.out.print(trainData_AL.get(item2).toString());
                System.out.format("%15f", DM2[item2]);
                System.out.println();
            }
            System.out.println();
            System.out.printf("Gerçek Tür: %2s   Tahminlenen Tür: %2s ", gercekTur, tur2);
            System.out.println();
            System.out.println(item.toString());

        }
        System.out.println();
        int rate = testData_AL.size()/100;
        System.out.print("BAŞARI ORANI: %");       //başarı oranı yazdırılıyor
        System.out.println(correctValue/rate);
        System.out.println();


        System.out.println("...Veriseti Yazdırılıyor...");
        for(Banknot item:trainData_AL){
            System.out.println(item.toString());
        }

    }

    public static double [] distance(Banknot kullaniciNesne, ArrayList<Banknot> arrayList ){
        double[] DM = new double[arrayList.size()];
        //verilen arraylistteki her bir nesne ile test setindeki her bir nesne arasındaki uzaklık hesaplanıyor
        double ilkVaryans = kullaniciNesne.getVaryans();
        double ilkCarpiklik = kullaniciNesne.getCarpiklik();
        double ilkBasiklik = kullaniciNesne.getBasiklik();
        double ilkEntropi = kullaniciNesne.getEntropi();

        for (int i = 0; i<arrayList.size(); i++){
            double sonVaryans = arrayList.get(i).getVaryans();
            double sonCarpiklik = arrayList.get(i).getCarpiklik();
            double sonBasiklik = arrayList.get(i).getBasiklik();
            double sonEntropi = arrayList.get(i).getEntropi();

            double farkV = Math.pow(sonVaryans-ilkVaryans, 2);
            double farkC = Math.pow(sonCarpiklik-ilkCarpiklik, 2);
            double farkB = Math.pow(sonBasiklik-ilkBasiklik, 2);
            double farkE = Math.pow(sonEntropi-ilkEntropi, 2);

            double distance = Math.sqrt(farkV + farkC + farkB + farkE);

            DM[i] = distance;

        }

        return DM;

    }

    public static int turBelirle(double[] DM, int k, ArrayList<Banknot> arrayList, int [] kkomsu ){
        double[] copyDM = DM.clone();
        Arrays.sort(copyDM);       //uzaklıklara göre sıralanıyor
        int[] minIndexList = new int[k];


        for (int i = 0; i < k; i++) {      // en küçük k komşunun indexleri tutuluyor
            double min = copyDM[i];
            for (int j = 0; j < DM.length; j++) {
                if(min == DM[j]){
                    minIndexList[i] = j;
                }
            }
        }

        int tur0 = 0;
        int tur1 = 0;                    // en yakın k komşunun türlerine göre tür atama işlemi yapılıyor
        for(int item:minIndexList){
            int tur = arrayList.get(item).getTur();
            if(tur == 0){
                tur0 +=1;
            }
            else{
                tur1 += 1;
            }
        }
        for(int a =0; a<minIndexList.length; a++){
            kkomsu[a] = minIndexList[a];
        }

        if(tur1 > tur0)
            return 1;
        else if(tur0> tur1)
            return 0;
        else
            return arrayList.get(minIndexList[0]).getTur();
    }

    public static ArrayList<Banknot> readFİle(String filePath, ArrayList<Banknot> arrayList){
        // try catch blokları arasında dosyadaki veriler okunarak banknot nesnesi oluşturuluyor ve gönderilen
        // arrayliste ekleniyor

        try {
            File file1 = new File(filePath);
            Scanner file = new Scanner(file1);

            try {
                while (file.hasNext()){
                    String satir = file.next();
                    satir = satir.replace(",", " ");

                    String[] aliste = satir.split(" ", 5);
                    double varyans = Double.parseDouble(aliste[0]);
                    double carpiklik = Double.parseDouble(aliste[1]);
                    double basiklik = Double.parseDouble(aliste[2]);
                    double entropi = Double.parseDouble(aliste[3]);
                    int tur = Integer.parseInt(aliste[4]);

                    Banknot newBanknot = new Banknot(varyans, carpiklik, basiklik, entropi, tur);

                    arrayList.add(newBanknot);

                }
                file.close();
            } catch (NoSuchElementException e) {
                System.out.println("Dosyada Başka Eleman Kalmadı!");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Dosya Bulunamadı!" + e.toString());
            System.exit(0);
        }

        return arrayList;
    }

    public static Banknot getInput(){           // kullanıcıdan değerler larak nesne oluşturuyor
        Scanner klavye = new Scanner(System.in);
        System.out.print("Varyans Değerini Giriniz: ");
        double userVaryans = Double.parseDouble(klavye.next());
        System.out.print("Çarpıklık Değerini Giriniz: ");
        double userCarpiklik = Double.parseDouble(klavye.next());
        System.out.print("Basıklık Değerini Giriniz: ");
        double userBasiklik = Double.parseDouble(klavye.next());
        System.out.print("Entropi Değerini Giriniz: ");
        double userEntropi = Double.parseDouble(klavye.next());

        Banknot userObject = new Banknot(userVaryans, userCarpiklik, userBasiklik, userEntropi, 0);

        return userObject;
    }
}
