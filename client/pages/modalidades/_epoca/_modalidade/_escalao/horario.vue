<template>
    <div>
        <v-dialog v-model="dialogAula" max-width="400px">
            <v-card>
                <v-card-title>Criar Aula</v-card-title>
                <v-card-text>
                    <v-form>
                        <v-select label="Day:"v-model="form.dia" :items="days" required></v-select>
                        <v-text-field label="Hora Inicio" v-model="form.horaInicio" type="number"/>
                        <v-text-field label="Hora Inicio" v-model="form.horaFim" type="number"/>
                        <v-textarea v-model="form.description" label=" Description"
                                    prepend-icon="mdi-comment"></v-textarea>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer/>
                    <v-btn @click="dialogAula=false" color="primary">Cancelar</v-btn>
                    <v-btn @click="salvar" color="green">Salvar</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <h3>HORARIO|{{this.route.escalao}}</h3>
        <v-btn :to="'/modalidades'"color="cyan">Voltar</v-btn>
        <v-btn @click="openCreateAula" color="primary">Criar Aula</v-btn>
        <v-data-table hide-default-footer :items="diasC" :headers="headersHorarioS">
            <template v-slot:item.SEGUNDA_FEIRA="{item}">
                <v-card>
                    <v-data-table hide-default-footer :headers="headersDia" :items="item.SEGUNDA_FEIRA">
                        <template v-slot:item.horaInicio="{item}">
                            {{item.horaInicio +'pm'}}
                        </template>
                    </v-data-table>
                </v-card>
            </template>
            <template v-slot:item.TERCA_FEIRA="{item}">
                <v-card>
                    <v-data-table hide-default-footer :headers="headersDia"
                                  :items="item.TERCA_FEIRA"></v-data-table>
                </v-card>
            </template>
            <template v-slot:item.QUARTA_FEIRA="{item}">
                <v-card>
                    <v-data-table hide-default-footer  :headers="headersDia"
                                  :items="item.QUARTA_FEIRA"></v-data-table>
                </v-card>
            </template>
            <template v-slot:item.QUINTA_FEIRA="{item}">
                <v-data-table hide-default-footer  :headers="headersDia"
                              :items="item.QUINTA_FEIRA"></v-data-table>
            </template>
            <template v-slot:item.SEXTA_FEIRA="{item}">
                <v-data-table hide-default-footer  :headers="headersDia"
                              :items="item.SEXTA_FEIRA"></v-data-table>
            </template>
        </v-data-table>

    </div>
</template>

<script>
    export default {
        data: function () {
            return {
                route: {
                    epoca: '', modalidade: '', escalao: ''
                },
                headersHorarioS: [
                    {
                        value: 'SEGUNDA_FEIRA',
                        text: 'SEGUNDA_FEIRA',
                        sortable: false,
                        class: ['red', 'white--text'],
                        width: '200px'
                    },
                    {
                        value: 'TERCA_FEIRA',
                        text: 'TERCA_FEIRA',
                        sortable: false,
                        class: ['yellow', 'black--text'],
                        width: '200px'
                    },
                    {
                        value: 'QUARTA_FEIRA',
                        text: 'QUARTA_FEIRA',
                        sortable: false,
                        class: ['green', 'white--text'],
                        width: '200px'
                    },
                    {
                        value: 'QUINTA_FEIRA',
                        text: 'QUINTA_FEIRA',
                        sortable: false,
                        class: ['indigo', 'white--text'],
                        width: '200px'
                    },
                    {
                        value: 'SEXTA_FEIRA',
                        text: 'SEXTA_FEIRA',
                        sortable: false,
                        class: ['cyan', 'white--text'],
                        width: '200px'
                    }
                ],
                headersDia: [
                    {value: 'horaInicio', text: 'Hora Inicio', sortable: false},
                    {value: 'horaFim', text: 'Hora Fin', sortable: false},
                    {value: 'description', text: 'Descricao', sortable: false}
                ],
                dias: [],
                diasC: [],
                dialogAula: false,
                form: {
                    dia: '',
                    description: '',
                    horaInicio: undefined,
                    horaFim: undefined
                },
                days: [
                    {value: 'SEGUNDA_FEIRA', text: 'SEGUNDA_FEIRA'},
                    {value: 'TERCA_FEIRA', text: 'TERCA_FEIRA'},
                    {value: 'QUARTA_FEIRA', text: 'QUARTA_FEIRA'},
                    {value: 'QUINTA_FEIRA', text: 'QUINTA_FEIRA'},
                    {value: 'SEXTA_FEIRA', text: 'SEXTA_FEIRA'}
                ]
            }
        },
        created() {
            this.route.modalidade = this.$route.params.modalidade;
            this.route.escalao = this.$route.params.escalao;
            this.route.epoca = this.$route.params.epoca;
            this.$axios.get('/api/modalidades/' + this.route.epoca + '/' + this.route.modalidade + '/' + this.route.escalao + '/horario')
                .then(horario => {
                    console.log(horario)
                    this.dias = horario.data.dias
                    //console.log(this.dias)
                    this.convertArray();
                })
        },
        methods: {
            convertArray() {
                var obj = {}
                var arry = []
                for (let i = 0; i < this.dias.length; i++) {
                    obj[this.dias[i].dia] = this.dias[i].aulas
                }
                arry.push(obj)
                this.diasC = arry;
                console.log(arry)
            },
            openCreateAula(){
                this.dialogAula=true;
            },
            salvar(){
                var data=JSON.stringify(this.form);
                this.$axios({
                    method: 'post',
                    data:data,
                    url: '/api/modalidades/'+ this.route.epoca + '/' + this.route.modalidade + '/' + this.route.escalao,
                    headers: {'Content-Type': 'application/json'}
                }).then(horario => {
                    this.dialogAula=false;
                    this.$axios.get('/api/modalidades/' + this.route.epoca + '/' + this.route.modalidade + '/' + this.route.escalao + '/horario')
                        .then(horario => {
                            console.log(horario)
                            this.dias = horario.data.dias
                            //console.log(this.dias)
                            this.convertArray();
                        })
                })
            }
        }
    }
</script>

<style scoped>

</style>