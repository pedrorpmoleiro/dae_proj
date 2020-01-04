<template>
  <v-card>
    <div v-if="userLogado.tipo=='ADMINISTRADOR'">
    <v-card-title>
      <v-text-field v-model="search" label="Search" append-icon="search" single-line hide-details />
    </v-card-title>
    <v-data-table :headers="headers" :items="users" :search="search" class="elevation-1">
      <template v-slot:top>
        <v-dialog v-model="dialog" max-width="500px">
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.name" label="Name" />
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.email" label="Email" />
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field disabled v-model="editedItem.username" label="Username" />
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer />
              <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="save">Save</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </template>
      <template v-slot:item.action="{ item }">
        <v-icon small class="mr-2" @click="editItem(item)">edit</v-icon>
        <v-icon small @click="deleteItem(item)">delete</v-icon>
      </template>
    </v-data-table>
    <v-btn class="mx-2" fab dark color="indigo" href="utilizadores/create">
      <v-icon dark>mdi-plus</v-icon>
    </v-btn>
    </div>
    <div v-if="userLogado.tipo != 'ADMINISTRADOR'">
      <v-banner>Only the admin could use this page.</v-banner>
    </div>
  </v-card>
</template>

<script>
export default {
  data: () => ({
    dialog: false,
    search: "",
    headers: [
      { text: "ID", value: "idSocio" },
      { text: "Nome", value: "name" },
      { text: "E-mail", value: "email" },
      { text: "Username", value: "username" },
      { text: "Tipo", value: "tipo" },
      { text: "Actions", value: "action", sortable: false }
    ],
    userLogado:{
      tipo: undefined,
      username: undefined
    },
    users: [],
    editedIndex: -1,
    editedItem: {
      name: "",
      email: ""
    },
    defaultItem: {
      name: "",
      email: ""
    }
  }),
  mounted() {
    this.$axios.$get("/api/users").then(response => {
      console.log(response);
      this.users = response;
    });
      if (this.userLogado.username != null) {
        this.isUserAutenticado = true;
      } else {
        let userUsername = localStorage.getItem("username");

        if (userUsername == null) {
          console.log("Não esta logado");
          this.$router.push("/login");
          return;
        }
        console.log(userUsername);
        this.$axios({
          method: "get",
          url: "api/users/" + userUsername,
          headers: { "Content-Type": "application/json" }
        })
                .then(response => {
                  //console.log(response)
                  this.userLogado.tipo = response.data.tipo;
                  this.userLogado.username = response.data.username;
                })
                .catch(error => {
                  console.log(error);
                  console.log(this.$axios.defaults.headers);
                });
      }
  },
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "New User" : "Edit User";
    }
  },

  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  methods: {
    editItem(item) {
      this.editedIndex = this.users.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    deleteItem(item) {
      var userName = item.username;
      const index = this.users.indexOf(item);
      if (confirm("Are you sure you want to delete this item?")) {
        this.users.splice(index, 1);
        this.$axios
          .$delete("/api/users/" + userName)
          .then(() => {
            console.log("User deleted successfull");
          })
          .catch(error => {
            if (error.response != undefined) {
              this.errorMsg = error.response.data;
            }
          });
      }
    },

    close() {
      this.dialog = false;
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      }, 300);
    },

    save() {
      var userName = this.editedItem.username;
      console.log("/api/users/" + userName);
      if (this.editedIndex > -1) {
        Object.assign(this.users[this.editedIndex], this.editedItem);
        this.$axios
          .$put("/api/users/" + userName, {
            name: this.editedItem.name,
            email: this.editedItem.email
          })
          .then(function(response) {
            console.log("User updated successfully");
          })
          .catch(error => {
            this.errorMsg = error.response.data;
            console.log(this.errorMsg);
          });
      } else {
        this.users.push(this.editedItem);
      }
      this.close();
    }
  }
};
</script>

