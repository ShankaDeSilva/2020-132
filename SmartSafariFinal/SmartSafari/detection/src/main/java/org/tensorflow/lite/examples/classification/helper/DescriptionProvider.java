package org.tensorflow.lite.examples.classification.helper;


public class DescriptionProvider {

    public static String getDescription(String animal_name) {
        if (animal_name.equals("Bear")) {
            return getBearDesc();
        } else if (animal_name.equals("Leopard")) {
            return getLeopardDesc();
        } else if (animal_name.equals("Black-naped_hare")) {
            return getBlackNapedHareDesc();
        }
        return getOtherDesc();
    }

    private static String getBearDesc() {
        return "SLOTH BEAR  –   The distinctly shaped sloth bear with its black shaggy coat and heavy build is often spotted in several of Sri Lanka’s National Parks where there is undisturbed forest growth, especially in Yala and Wilpattu. Found exclusively in the Indian Subcontinent, the sloth bear has evolved from the Eurasian Brown Bear over several millennia.\n" +
                "\n" +
                "Sloth Bears are insect-eating mammals and have a specially adapted lower lip and palate for gathering their food. They mainly consume termites, bee hives, and fruits, and can be heard from a distance of 100 metres (300 feet) away as they ‘slurp’ up their food. They are known to hunt smaller mammals as well.";
    }

    private static String getLeopardDesc() {
        return "Sri Lankan Leopard – One of the most lithe and supple of the big cats, the Sri Lankan leopard holds a mystique like no other. Coming out of its slumber at dawn and dusk to swagger through the jungle environments, the leopard is a creature of both incredibly terrifying strength and beauty.\n" +
                "\n" +
                "Sri Lanka’s Yala National Park is recognized as having the highest density of wild leopards in the world. In one area of the Park, it is reported that there is one leopard per square kilometre, an incredibly high number compared to anywhere else in the world.  Unconfirmed reports indicate that between 500 – 650 leopards reside in the wild in Sri Lanka.";
    }

    private static String getBlackNapedHareDesc() {
        return "Black-naped Hare (Lepus nigricollis )\n" +
                "\n" +
                "Family: Leporidae.\n" +
                "\n" +
                "Yala National Park, Sri Lanka.\n" +
                "\n" +
                "Black-naped Hare is well distributed in scrub and grasslands throughout the island. It is nocturnal in habit in most areas and during day time lies up in a well camouflaged sheltered patch in the undergrowth. But in the protected areas such as in national parks, it is active even during the day time, mostly in the morning hours. Black-naped Hare is an almost entirely herbivorous mammal and feeds on grasses, shoots, young leaves etc. It doesn't have definite breeding season and produces one or two young ‘leverets’ at any time of the year.";
    }

    private static String getOtherDesc() {
        return "Peacocks are a common sight they aren't in mammal category. They could be displaying its fan of feathers or perched up and camouflaged at the top of trees. Peafowl – which is the actual term for peacocks and peahens together, have an average life span of 20 years when living in the wild.";
    }
}
