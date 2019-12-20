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
    ></v-data-table>
    <v-btn class="mx-2" fab dark color="indigo" href = "utilizadores/create">
      <v-icon dark>mdi-plus</v-icon>
    </v-btn>
  </v-card>
</template>

<script>
    export default {
        data() {
            return {
                search: '',
                headers: [
                    {text: 'ID', value: 'idSocio'},
                    {text: 'Nome', value: 'name'},
                    {text: 'E-mail', value: 'email'},
                    {text: 'Username', value: 'username'},
                    {text: 'Tipo', value: 'tipo'}
                ], users: []
            }
        }, mounted() {
            this.$axios.$get('/api/users').then((response) => {
               console.log(response)
                this.users = response
            })
        }
    }
</script>

<style scoped>

</style>
