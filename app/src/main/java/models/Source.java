    package models;

    import org.parceler.Parcel;

    @Parcel
    public class Source {
        String id ="";
        String name = "";

        public Source() {
        }

        public String getId() { return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
