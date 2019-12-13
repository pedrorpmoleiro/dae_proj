<template>
  <v-card>
    <v-card-title>
      <v-text-field
        v-model="search"
        append-icon="search"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="desserts"
      :search="search"
    ></v-data-table>
  </v-card>
</template>

<script>
    export default {
        data() {
            return {
                fields: ['username', 'name', 'email', 'courseName', 'actions'], students: []
            }
        }, created() {
            this.$axios.$get('/api/students').then((students) => {
                this.students = students
            })
        }
</script>

<style scoped>

</style>
