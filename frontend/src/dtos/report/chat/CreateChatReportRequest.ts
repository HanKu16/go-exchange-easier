export type CreateChatReportRequest = {
    description: string | null;
    reason: string;
    reportedUserId: number;
    roomId: string;
}