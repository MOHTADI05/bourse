package tn.esprit.mfb.Services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.ProfilSolvabiteRepo;
import tn.esprit.mfb.entity.ProfilSolvabilite;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClusteringService {

private final ProfilSolvabiteRepo profilSolvabiteRepo;
    public void segmentation() throws Exception {
        // Charger les données à partir d'un fichier ARFF (ou tout autre format pris en charge par Weka)
        //DataSource source = new DataSource("C:\\Users\\USER\\Desktop\\dataset\\lesrevenus.csv");
        DataSource source = new DataSource("C:\\Users\\Asus VivoBook\\Desktop\\DATAclustert\\exportt.csv");

        Instances data = source.getDataSet();

        // Créer un modèle de clustering (K-means avec k=3)
        SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setNumClusters(2);
        kmeans.buildClusterer(data);

        // Afficher les résultats de la segmentation
        for (int i = 0; i < data.numInstances(); i++) {
            int cluster = kmeans.clusterInstance(data.instance(i));
            if(cluster+1==1) {
                System.out.println("Client " + (i + 1) + " est Solvable dans le cluster " + (cluster + 1));
            }else{
                System.out.println("Client " + (i + 1) + " est NON Solvable dans le cluster " + (cluster + 1));

            }
        }
    }


    public int segmentation1(ProfilSolvabilite p) throws Exception {
        // Charger les données à partir d'un fichier ARFF (ou tout autre format pris en charge par Weka)
        //DataSource source = new DataSource("C:\\Users\\USER\\Desktop\\dataset\\lesrevenus.csv");
        DataSource source = new DataSource("C:\\Users\\Asus VivoBook\\Desktop\\DATAclustert\\exportt.csv");

        Instances data = source.getDataSet();

        // Créer un modèle de clustering (K-means avec k=2)
        SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setNumClusters(2);
        kmeans.buildClusterer(data);

        // Afficher les résultats de la segmentation
        int cluster = kmeans.clusterInstance(data.instance(data.numInstances()-1));
        System.out.println(cluster+1);
        if(cluster+1==1){
            System.out.println("Profil " + p.getId() + " est Solvable il appratien au cluster" + (cluster + 1));
            return cluster+1;
        }else{
            System.out.println("Profil " + p.getId() + " est NON Solvable il appratien au cluster " + (cluster + 1));
            return cluster+1;
        }


    }


    public double[] segmentation2() throws Exception {
        // Charger les données à partir d'un fichier ARFF (ou tout autre format pris en charge par Weka)
        DataSource source = new DataSource("C:\\Users\\Asus VivoBook\\Desktop\\DATAclustert\\exportt.csv");
        Instances data = source.getDataSet();

        // Créer un modèle de clustering (K-means avec k=2)
        SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setNumClusters(2);
        kmeans.buildClusterer(data);

        // Obtenir les tailles des clusters
        double[] clusterSizes = kmeans.getClusterSizes();

        // Calculer les pourcentages
        double totalInstances = data.numInstances();
        double percentageCluster1 = (clusterSizes[0] / totalInstances) * 100;
        double percentageCluster2 = (clusterSizes[1] / totalInstances) * 100;

        // Retourner les pourcentages dans un tableau de doubles
        return new double[] { percentageCluster1, percentageCluster2 };
    }
    public List<Instance> getCluster2Instances() throws Exception {
        List<Instance> cluster2Instances = new ArrayList<>();

        DataSource source = new DataSource("C:\\Users\\Asus VivoBook\\Desktop\\DATAclustert\\exportt.csv");
        Instances data = source.getDataSet();

        SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setNumClusters(2);
        kmeans.buildClusterer(data);

        for (int i = 0; i < data.numInstances(); i++) {
            int cluster = kmeans.clusterInstance(data.instance(i));
            if (cluster + 1 == 2) { // Vérifier si l'instance appartient au cluster 2
                cluster2Instances.add(data.instance(i));
            }
        }

        return cluster2Instances;
    }

    public List<Double> cluster2() throws Exception {
        // Récupérer les instances appartenant au cluster 2
        List<Instance> cluster2Instances = getCluster2Instances();

        // Initialiser une liste pour stocker les moyennes des variables pour le cluster 2
        List<Double> variableAverages = new ArrayList<>();
        double totalVariable1 = 0;
        double totalVariable2 = 0;
        double totalVariable3 = 0;
        int numInstances = cluster2Instances.size();

        // Calculer la somme des valeurs de chaque variable pour toutes les instances
        for (Instance instance : cluster2Instances) {
            totalVariable1 += instance.value(0);
            totalVariable2 += instance.value(1);
            totalVariable3 += instance.value(4);
        }

        // Calculer la moyenne de chaque variable
        double averageVariable1 = totalVariable1 / numInstances;
        double averageVariable2 = totalVariable2 / numInstances;
        double averageVariable3 = totalVariable3 / numInstances;

        // Ajouter les moyennes des variables à la liste
        variableAverages.add(averageVariable1);
        variableAverages.add(averageVariable2);
        variableAverages.add(averageVariable3);

        return variableAverages;
    }

    public List<Instance> getCluster1Instances() throws Exception {
        List<Instance> cluster2Instances = new ArrayList<>();

        DataSource source = new DataSource("C:\\Users\\Asus VivoBook\\Desktop\\DATAclustert\\exportt.csv");
        Instances data = source.getDataSet();

        SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setNumClusters(2);
        kmeans.buildClusterer(data);

        for (int i = 0; i < data.numInstances(); i++) {
            int cluster = kmeans.clusterInstance(data.instance(i));
            if (cluster + 1 == 1) { // Vérifier si l'instance appartient au cluster 2
                cluster2Instances.add(data.instance(i));
            }
        }

        return cluster2Instances;
    }

    public List<Double> cluster1() throws Exception {
        // Récupérer les instances appartenant au cluster 2
        List<Instance> cluster2Instances = getCluster1Instances();

        // Initialiser une liste pour stocker les moyennes des variables pour le cluster 2
        List<Double> variableAverages = new ArrayList<>();
        double totalVariable1 = 0;
        double totalVariable2 = 0;
        double totalVariable3 = 0;
        int numInstances = cluster2Instances.size();

        // Calculer la somme des valeurs de chaque variable pour toutes les instances
        for (Instance instance : cluster2Instances) {
            totalVariable1 += instance.value(0);
            totalVariable2 += instance.value(1);
            totalVariable3 += instance.value(4);
        }

        // Calculer la moyenne de chaque variable
        double averageVariable1 = totalVariable1 / numInstances;
        double averageVariable2 = totalVariable2 / numInstances;
        double averageVariable3 = totalVariable3 / numInstances;

        // Ajouter les moyennes des variables à la liste
        variableAverages.add(averageVariable1);
        variableAverages.add(averageVariable2);
        variableAverages.add(averageVariable3);

        return variableAverages;
    }





}
