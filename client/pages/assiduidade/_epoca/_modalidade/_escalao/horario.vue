<template>
    <div>
        <v-dialog v-model="dialogNewAss" max-width="400px">
            <v-card>
                <v-card-title>Criar Assistude</v-card-title>
                <v-card-text>
                    <v-form>
                        <v-select label="Day:"v-model="form.assistude" :items="days" required >
                        </v-select>
                        <v-text-field label="username" v-model="form.username" type="text"/>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer/>
                    <v-btn @click="dialogNewAss=false" color="primary">Cancelar</v-btn>
                    <v-btn @click="salvar" color="green">Salvar</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <v-dialog v-model="dialog" max-width="500px">
            <v-card>
                <v-card-title>Assistudes</v-card-title>
                <v-btn color="cyan" @click="dialogNewAss=true">New Assistude</v-btn>
                <v-data-table  v-if="aula.assistudes.length":headers="headersAula" :items="aula.assistudes">
                    <template v-slot:item.assistude="{item}">
                        <v-icon v-if="item.assistude=='COMPARECEU'"color="green">mdi-check-bold</v-icon>
                        <v-icon v-else color="red">mdi-close-circle</v-icon>
                    </template>
                </v-data-table>

                <v-card-actions>
                    <v-btn @click="dialog=false" color="primary">Close</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <h3>HORARIO ASSIDUIDADE</h3>
        <v-btn :to="'/assiduidade'"color="cyan">Voltar</v-btn>
        <v-data-table hide-default-footer :items="diasC" :headers="headersHorarioS">
            <template v-slot:item.SEGUNDA_FEIRA="{item}">
                <v-card>
                    <v-data-table hide-default-footer :headers="headersDia" :items="item.SEGUNDA_FEIRA">
                        <template v-slot:item.horaInicio="{item}">
                            {{item.horaInicio+':00'}}
                        </template>
                        <template v-slot:item.horaFim="{item}">
                            {{item.horaFim+':00'}}
                        </template>
                        <template v-slot:item.action="{item}">
                            <v-btn @click="registar(item,'SEGUNDA_FEIRA')" color="red">Registar</v-btn>
                        </template>
                    </v-data-table>
                </v-card>
            </template>
            <template v-slot:item.TERCA_FEIRA="{item}">
                <v-card>
                    <v-data-table hide-default-footer :headers="headersDia"  :items="item.TERCA_FEIRA">
                        <template v-slot:item.horaInicio="{item}">
                            {{item.horaInicio+':00'}}
                        </template>
                        <template v-slot:item.horaFim="{item}">
                            {{item.horaFim+':00'}}
                        </template>
                        <template v-slot:item.action="{item}">
                            <v-btn @click="registar(item,'TERCA_FEIRA')" color="yellow">Registar</v-btn>
                        </template>
                    </v-data-table>
                </v-card>
            </template>
            <template v-slot:item.QUARTA_FEIRA="{item}">
                <v-card>
                    <v-data-table hide-default-footer  :headers="headersDia" :items="item.QUARTA_FEIRA">
                        <template v-slot:item.horaInicio="{item}">
                            {{item.horaInicio+':00'}}
                        </template>
                        <template v-slot:item.horaFim="{item}">
                            {{item.horaFim+':00'}}
                        </template>
                        <template v-slot:item.action="{item}">
                            <v-btn @click="registar(item,'QUARTA_FEIRA')" color="green">Registar</v-btn>
                        </template>
                    </v-data-table>
                </v-card>
            </template>
            <template v-slot:item.QUINTA_FEIRA="{item}">
                <v-data-table hide-default-footer  :headers="headersDia" :items="item.QUINTA_FEIRA">
                    <template v-slot:item.horaInicio="{item}">
                        {{item.horaInicio+':00'}}
                    </template>
                    <template v-slot:item.horaFim="{item}">
                        {{item.horaFim+':00'}}
                    </template>
                    <template v-slot:item.action="{item}">
                        <v-btn @click="registar(item,'QUINTA_FEIRA')" color="blue">Registar</v-btn>
                    </template>
                </v-data-table>
            </template>
            <template v-slot:item.SEXTA_FEIRA="{item}">
                <v-data-table hide-default-footer  :headers="headersDia"  :items="item.SEXTA_FEIRA">
                    <template v-slot:item.horaInicio="{item}">
                        {{item.horaInicio+':00'}}
                    </template>
                    <template v-slot:item.horaFim="{item}">
                        {{item.horaFim+':00'}}
                    </template>
                    <template v-slot:item.action="{item}">
                        <v-btn @click="registar(item,'SEXTA_FEIRA')" color="cyan">Registar</v-btn>
                    </template>
                </v-data-table>
            </template>
        </v-data-table>
    </div>

</template>

<script>
    export default {
        data: function () {
            return {
                dialogNewAss:false,
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
                    {value: 'description', text: 'Descricao', sortable: false},
                    {value: 'action', text: 'Acciones', sortable: false}
                ],
                dias: [],
                diasC: [],
                dialogAula: false,
                form: {
                    assistude:'',
                    username:''
                },
                days: [
                    {value: 'COMPARECEU', text: 'COMPARECEU'},
                    {value: 'FALTOU', text: 'FALTOU'},
                ],
                aula:{
                    assistudes:[]
                },
                headersAula:[
                    {text:'Assistude',value:'assistude',sortable:false},
                    {text:'Username',value:'username',sortable:false}
                ],
                editItemAssitude:{
                    assistude:'',
                    username:''
                },
                selectedItemAula:{
                    horaInicio:'',
                    horaFim:'',
                    description:''
                },
                dialog:false,
                username:'',
                dia:''
            }
        },
        created() {
            this.username=localStorage.getItem('username');
            this.route.modalidade = this.$route.params.modalidade;
            this.route.escalao = this.$route.params.escalao;
            this.route.epoca = this.$route.params.epoca;
            this.$axios.get('/api/modalidades/' + this.route.epoca + '/' + this.route.modalidade + '/' + this.route.escalao + '/horario')
                .then(horario => {
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
            },
            registar(item,dia){
                //console.log(item)
                this.dia=dia;
                this.selectedItemAula=Object.assign({}, item);
                var routeS='/api/modalidades/' + this.username+'/'+this.route.escalao + '/'
                    + this.route.epoca + '/' + dia + '/'+this.selectedItemAula.horaInicio+'/'+this.selectedItemAula.horaFim+'/aula';

                this.$axios.get(routeS)
                    .then(horario => {
                        this.aula = horario.data
                        this.dialog=true;
                    })


            },
            salvar(){
                var data=JSON.stringify(this.form);
                var routeS='/api/modalidades/' + this.username+'/'+this.route.escalao + '/'
                    + this.route.epoca + '/' + this.dia + '/'+this.selectedItemAula.horaInicio+'/'+this.selectedItemAula.horaFim;

                this.$axios({
                    method: 'post',
                    data:data,
                    url: routeS,
                    headers: {'Content-Type': 'application/json'}
                }).then(h => {
                    this.dialogNewAss=false;
                    this.$axios.get('/api/modalidades/' + this.route.epoca + '/' + this.route.modalidade + '/' + this.route.escalao + '/horario')
                        .then(horario => {
                            this.dias = horario.data.dias
                            //console.log(this.dias)
                            this.convertArray();
                            var routeS='/api/modalidades/' + this.username+'/'+this.route.escalao + '/'
                                + this.route.epoca + '/' + this.dia + '/'+this.selectedItemAula.horaInicio+'/'+this.selectedItemAula.horaFim+'/aula';
                            this.$axios.get(routeS)
                                .then(horario => {
                                    this.aula = horario.data
                                    this.dialog=true;
                                })
                        })
                })

            }
        }
    }
</script>

<style scoped>

</style>