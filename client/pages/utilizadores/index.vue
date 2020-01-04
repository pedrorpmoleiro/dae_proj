<template>
  <v-card>
    <v-card-title>
      <v-text-field
              v-model="search"
              label="Search"
              append-icon="search"
              single-line
              hide-details
      ></v-text-field>
    </v-card-title>
  <v-data-table
          :headers="headers"
          :items="users"
          :search="search"
          class="elevation-1"
  >
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
                    <v-text-field v-model="editedItem.name" label="Name"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.username" label="Email"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field disabled v-model="editedItem.username" label="Username"></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="save">Save</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
    </template>
    <template v-slot:item.action="{ item }">
      <v-icon
              small
              class="mr-2"
              @click="editItem(item)"
      >
        edit
      </v-icon>
      <v-icon
              small
              @click="deleteItem(item)"
      >
        delete
      </v-icon>
    </template>
  </v-data-table>
    <v-btn class="mx-2" fab dark color="indigo" href = "utilizadores/create">
      <v-icon dark>mdi-plus</v-icon>
    </v-btn>
  </v-card>
</template>

<script>
  export default {
    data: () => ({
      dialog: false,
      search: '',
      headers: [
        {text: 'ID', value: 'idSocio'},
        {text: 'Nome', value: 'name'},
        {text: 'E-mail', value: 'username'},
        {text: 'Username', value: 'username'},
        {text: 'Tipo', value: 'tipo'},
        { text: 'Actions', value: 'action', sortable: false },
      ],
      users: [],
      editedIndex: -1,
      editedItem: {
        name: '',
        username: '',
        username: ''
      },
      defaultItem: {
        name: '',
        username: '',
        username: ''
      },
    }),
    mounted() {
      this.$axios.$get('/api/users').then((response) => {
        console.log(response)
        this.users = response
      })
    },
    computed: {
      formTitle () {
        return this.editedIndex === -1 ? 'New User' : 'Edit User'
      },
    },

    watch: {
      dialog (val) {
        val || this.close()
      },
    },

    methods: {
      editItem (item) {
        this.editedIndex = this.users.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialog = true
      },

      deleteItem (item) {
        var userName = item.username;
        const index = this.users.indexOf(item)
        if(confirm('Are you sure you want to delete this item?')){
          this.users.splice(index, 1);
          this.$axios.$delete('/api/users/'+userName)
                  .then(() => {
                    console.log("User deleted successfull");
                  })
                  .catch(error => {
                    if(error.response != undefined){
                    this.errorMsg = error.response.data
                    }
                  });
        }
      },

      close () {
        this.dialog = false
        setTimeout(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        }, 300)
      },

      save () {
        var userName = this.editedItem.username;
        console.log('/api/users/'+userName);
        if (this.editedIndex > -1) {
          Object.assign(this.users[this.editedIndex], this.editedItem)
          this.$axios.$put('/api/users/'+userName, {
            name: this.editedItem.name,
            username: this.editedItem.username,
          })
                  .then(function(response){
                    console.log("User updated successfully");
                  })
                  .catch(error => {
                    this.errorMsg = error.response.data
                    console.log(this.errorMsg)
                  });
        } else {
          this.users.push(this.editedItem)

        }
        this.close()
      },
    },
  }
</script>

