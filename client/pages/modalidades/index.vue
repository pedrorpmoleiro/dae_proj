<template>
    <div>
        <v-dialog v-model="dialogE" width="400px">
            <v-card>
                <v-card-title>
                    Nueva Epoca
                </v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col cols="15" md="6">
                                <v-text-field v-model="editItemEpoca.nome "
                                              label="Epoca nome"></v-text-field>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-card-text>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="dialogE=false">Cancel</v-btn>
                    <v-btn color="blue darken-1" text @click="saveEpoca">Save</v-btn>
                </v-card-actions>
            </v-card>

        </v-dialog>
        <v-dialog v-model="dialogEscaloes" width="650px">
            <v-card max-height="500px">
                <v-data-table hide-default-footer :items="escaloes" :headers="headersEscalao">
                    <template v-slot:top>
                        <h3>Informacao</h3>
                        <v-spacer/>
                        <v-btn @click="openDialogNewEscalao" class="primary">Criar Escalao</v-btn>
                    </template>
                    <template v-slot:no-results>
                        Não ha resultados
                    </template>
                    <template v-slot:item.action="{ item }">
                        <v-btn @click="editItemEpocaF(item)">
                            <v-icon>edit</v-icon>
                            Editar
                        </v-btn>
                        <v-btn :to="`/modalidades/${epocaSelected.nome}/${modalidadeSelected}/${item.name}`"
                               color="green">Ver Atletas e Treinadores
                        </v-btn>
                        <v-btn color="cyan" :to="`/modalidades/${epocaSelected.nome}/${modalidadeSelected}/${item.name}/horario`">Horario</v-btn>
                    </template>
                </v-data-table>
            </v-card>
            <v-btn color="red" @click="dialogEscaloes=false">Close</v-btn>
        </v-dialog>
        <v-dialog v-model="dialogcreateEscaloes" width="500px">
            <v-card>
                <v-card-title>
                    {{textEscalao}}
                </v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col cols="15" md="6">
                                <v-text-field v-model="editItemEsca.name "
                                              label="Escalao nome"></v-text-field>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-card-text>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="closeEscalao">Cancel</v-btn>
                    <v-btn color="blue darken-1" text @click="saveEscalao">Save</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <v-row>
            <v-col>
                <v-data-table :headers="headersE" item-key="nome" v-model="selected" :items="epocas"
                              :single-select="true" hide-default-footer show-select>
                    <template v-slot:top>
                        <h3>Epocas</h3>
                    </template>
                    <template v-slot:item.data-table-select="{isSelected, select,item}">
                        <v-simple-checkbox color="green" :value="isSelected" @input="select($event)"></v-simple-checkbox>
                    </template>
                    <template v-slot:footer>
                        <v-spacer></v-spacer>
                        <v-btn fab dark color="indigo" @click="dialogE=true">
                            <v-icon dark>mdi-plus</v-icon>
                        </v-btn>
                    </template>
                </v-data-table>
            </v-col>
            <v-col>
                <v-card width="950px" >
                    <v-card-title>Modalidades</v-card-title>
                    <div v-if="selected.length">
                        <v-card-title>
                            <v-text-field
                                    v-model="search"
                                    append-icon="search"
                                    label="Procurar"
                                    single-line
                                    hide-details
                            ></v-text-field>
                        </v-card-title>
                        <v-data-table :headers="headersM" item-key="nome" v-if="selected.length>0" :items="changeSelected"
                                      :items-per-page="5" :search="search"
                                      class="elevation-1">
                            <template v-slot:top>
                                <v-toolbar flat color="dark">
                                    <v-toolbar-title>Informação</v-toolbar-title>
                                    <v-divider class="mx-4" inset vertical></v-divider>
                                    Epoca {{selected[0].nome}}
                                    <v-spacer></v-spacer>
                                </v-toolbar>
                                <v-dialog v-model="dialog" max-width="500px">
                                    <template v-slot:activator="{ on }">
                                        <v-btn color="primary" dark class="mb-2" v-on="on" @click="isEdit=false">Nueva
                                            Modalidade
                                        </v-btn>
                                    </template>
                                    <v-card>
                                        <v-card-title>
                                            <span class="headline">{{textModalidade}}</span>
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
                                            <v-btn color="blue darken-1" text @click="saveModalidade">Save</v-btn>
                                        </v-card-actions>
                                    </v-card>
                                </v-dialog>
                            </template>
                            <template v-slot:item.action="{ item }">
                                <v-icon samll @click="getEscalao(item.nome)">details</v-icon>
                                <v-icon small class="mr-2" @click="editItemF(item)">edit</v-icon>
                                <v-icon small @click="deleteItem(item)"> delete</v-icon>
                            </template>
                            <template v-if="selected.length==0" v-slot:no-data>
                                NO DATA HERE!
                            </template>
                        </v-data-table>
                    </div>
                    <v-card-text v-else>
                        Por favor Escolha uma EPOCA
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
    </div>
</template>

<script>
    export default {
        name: "modalidades",
        data: function () {
            return {
                dialogcreateEscaloes: false,
                dialogEscaloes: false,
                dialogE: false,
                dialog: false,
                escaloes: [],
                epocas: [],
                selected: [],
                epocaSelected: {
                    nome: '',
                    modalidades: []
                },
                modalidadeSelected: '',
                headersE: [
                    {text: 'Nome', value: 'nome'}
                ],
                headersM: [
                    {text: 'Nome', align: 'left', sortable: false, value: 'nome'},
                    {text: '', value: 'data-table-expand'},
                    {text: 'Acciones', value: 'action', sortable: false}
                ],
                headersEscalao: [
                    {text: 'Nome', value: 'name'},
                    {text: 'Acciones', value: 'action', sortable: false}
                ],
                editItem: {
                    nome: ''
                },
                search: '',
                singleExpand: true,
                expanded: [],
                editItemEsca: {
                    name: ''
                }, editItemEpoca: {
                    nome: ''
                },
                isEdit: false,
                escalaoSelected: ''
            }
        },
        created() {
            this.getEpocas();
        },
        computed: {
            changeSelected() {
                this.epocaSelected = this.selected[0];
                return this.selected[0].modalidades;
            },
            textModalidade() {
                return !this.isEdit ? 'Nueva Modalidade' : 'Editar Modalidade'
            },
            textEscalao() {
                return !this.isEdit ? 'Nuevo Escalao' : 'Editar Escalao'
            }
        },
        methods: {
            closeEscalao() {
                this.dialogcreateEscaloes = false;
                this.editItemEsca.name = '';
            },
            close() {
                this.dialog = false;
                this.editItem.nome = '';
            },
            saveModalidade() {
                this.dialog = false;
                var data = JSON.stringify(this.editItem);
                if (this.isEdit) {
                    this.$axios({
                        method: 'put',
                        url: '/api/modalidades/' + this.epocaSelected.nome + '/' + this.modalidadeSelected,
                        data: data,
                        headers: {'Content-Type': 'application/json'}
                    })
                        .then(e => {
                                this.getEpocas();
                            }
                        )
                } else {
                    this.$axios({
                        method: 'post',
                        url: '/api/modalidades/' + this.epocaSelected.nome,
                        data: data,
                        headers: {'Content-Type': 'application/json'}
                    })
                        .then(e => {
                                this.getEpocas();
                            }
                        )
                }
            },
            saveEpoca() {
                this.dialogE = false;
                var data = JSON.stringify(this.editItemEpoca);
                this.$axios({
                    method: 'post',
                    url: '/api/modalidades/epoca',
                    data: data,
                    headers: {'Content-Type': 'application/json'}
                })
                    .then(e => {
                            this.getEpocas();
                            this.editItemEpoca.nome = '';
                        }
                    )


            },
            deleteItem(item) {
                item.nome
                confirm('Are you sure you want to delete this item?') &&
                this.$axios({method: 'delete', url: '/api/modalidades/' + this.epocaSelected.nome + '/' + item.nome})
                    .then(e => {
                        console.log('Delete')
                        this.getEpocas();

                    })
            },
            getEpocas() {
                this.$axios.get("/api/modalidades")
                    .then(r => {
                        this.epocas = r.data;
                    })
            },
            getEscalao: function (modalidade) {
                this.modalidadeSelected = modalidade;
                var escaloes = this.epocaSelected.modalidades.find(e => e.nome == modalidade).escaloes
                this.escaloes = escaloes;
                this.dialogEscaloes = true;
            },
            saveEscalao() {
                var data = JSON.stringify(this.editItemEsca);
                console.log(data)
                if (this.isEdit) {
                    this.$axios({
                        method: 'put',
                        url: '/api/modalidades/' + this.epocaSelected.nome + '/' + this.modalidadeSelected + '/' + this.escalaoSelected,
                        data: data,
                        headers: {'Content-Type': 'application/json'}
                    })
                        .then(e => {
                                this.editItemEsca.nome = '';
                                this.dialogcreateEscaloes = false;
                                this.escaloes = e.data;
                                this.getEpocas();
                            }
                        )

                } else {
                    this.$axios({
                        method: 'post',
                        url: '/api/modalidades/' + this.epocaSelected.nome + '/' + this.modalidadeSelected,
                        data: data,
                        headers: {'Content-Type': 'application/json'}
                    })
                        .then(e => {
                                this.editItemEsca.nome = '';
                                this.dialogcreateEscaloes = false;
                                this.escaloes = e.data;
                                this.getEpocas();
                            }
                        )
                }

            },
            editItemF(item) {
                this.modalidadeSelected = item.nome;
                this.isEdit = true;
                this.editItem = Object.assign({}, item);
                this.dialog = true;
            },
            editItemEpocaF(item) {
                this.escalaoSelected = item.name;
                this.isEdit = true;
                this.editItemEsca = Object.assign({}, item);
                this.dialogcreateEscaloes = true;
            },
            openDialogNewEscalao() {
                this.editItemEsca.name = '';
                this.dialogcreateEscaloes = true;
                this.isEdit = false;
            }

        }
    }
</script>

<style scoped>

</style>