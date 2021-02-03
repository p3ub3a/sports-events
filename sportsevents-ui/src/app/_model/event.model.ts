export interface Event {
    id: string,
    name: string,
    type: string,
    scheduledDate: string;
    closedDate: string;
    facilitator: string;
    location: string;
    status: string;
    outdoors: boolean;
    players: string[];
    winner: string;
    maxPlayers: number;
    closedBy: string;
}
