<template>
    <div>
        <v-card>
            <v-card-title>Modalidades</v-card-title>
            <v-card-title>
                <v-text-field
                        v-model="search"
                        append-icon="search"
                        label="Search"
                        single-line
                        hide-details
                ></v-text-field>
            </v-card-title>
            <v-data-table :headers="headers" :items="items" :items-per-page="5" :search="search" class="elevation-1">
                <template v-slot:top>
                    <v-toolbar flat color="dark">
                        <v-toolbar-title>My CRUD</v-toolbar-title>
                        <v-divider class="mx-4" inset vertical></v-divider>
                        <v-spacer></v-spacer>
                    </v-toolbar>
                    <v-dialog v-model="dialog" max-width="500px">
                        <template v-slot:activator="{ on }">
                            <v-btn color="primary" dark class="mb-2" v-on="on">New Item</v-btn>
                        </template>
                        <v-card>
                            <v-card-title>
                                <span class="headline">New Item</span>
                            </v-card-title>

                            <v-card-text>
                                <v-container>
                                    <v-row>
                                        <v-col cols="15" md="6">
                                            <v-text-field v-model="editItem.nome "
                                                          label="Modalidade nome"></v-text-field>
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
                    <v-icon small class="mr-2" @click="">edit</v-icon>
                    <v-icon small @click=""> delete</v-icon>
                </template>
            </v-data-table>
        </v-card>
    </div>
</template>

<script>
    export default {
        name: "modalidades",
        data: function () {
            return {
                dialog: false,
                items: [],
                headers: [{text: 'Name', align: 'left', sortable: false, value: 'nome'}, {
                    text: 'Actions',
                    value: 'action',
                    sortable: false
                }],
                editItem: {
                    nome: ''
                },
                search:''
            }
        },
        created() {
            this.$axios.get("/api/modalidades")
                .then(r => {
                    this.items = r.data;
                })
        },
        methods: {
            close() {
                this.dialog = false;
            },
            save() {
                this.dialog = false;
                var data = JSON.stringify(this.editItem);
                this.$axios({
                    method: 'post',
                    url: '/api/modalidades',
                    data: data,
                    headers: {'Content-Type': 'application/json'}
                })
                    .then(e => {
                            this.$axios.get("/api/modalidades")
                                .then(r => {
                                    this.items = r.data;
                                })
                        }
                    )
            }

        }
    }
</script>

<style scoped>

</style>